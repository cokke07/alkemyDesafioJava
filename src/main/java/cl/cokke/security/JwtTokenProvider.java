package cl.cokke.security;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import cl.cokke.exception.RestServiceException;
import cl.cokke.model.Role;
import cl.cokke.repository.UserRepository;
import cl.cokke.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key}")
	static SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	@Value("${security.jwt.token.expire-length}")
	private long validateInMilliseconds;

	@Autowired
	private UserService userService;
	

	@PostConstruct
	public static String convertSecretKeyToString() throws NoSuchAlgorithmException {
	    byte[] rawData = secretKey.getEncoded();
	    String encodedKey = Base64.getEncoder().encodeToString(rawData);
	    return encodedKey;
	}

	public String createToken(String username, List<Role> roles) {

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
				.filter(Objects::nonNull).collect(Collectors.toList()));
		Date now = new Date();
		Date validity = new Date(now.getTime() + validateInMilliseconds);
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(secretKey)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private String getUsername(String token) {
		//return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new RestServiceException("Expired or invalid JWT Token", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
