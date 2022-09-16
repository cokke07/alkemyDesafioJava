package cl.cokke.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.cokke.exception.RestServiceException;

public class JwtTokenFilter extends OncePerRequestFilter {

	/*
	 * Este filtro tiene las siguientes responsabilidades: 
	 * ● Verificar el token de acceso en el encabezado de autorización. Si se encuentra el token de acceso
	 * en el encabezado, se delega la autenticación a JwtTokenProvider, de lo
	 * contrario, arroja una excepción de autenticación. 
	 * ● Invoca estrategias de éxito o fracaso basadas en el resultado del proceso de autenticación
	 * realizado por JwtTokenProvider. 
	 * ● Se invoca chain.doFilter pasando como parámetros httpServletRequest, httpServletResponse, 
	 * tras una autenticación exitosa.
	 */
	
	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = jwtTokenProvider.resolveToken(request);

		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (RestServiceException e) {
			SecurityContextHolder.clearContext();
			response.sendError(e.getHttpStatus().value(), e.getMessage());
			return;
		}
		filterChain.doFilter(request, response);
	}
}
