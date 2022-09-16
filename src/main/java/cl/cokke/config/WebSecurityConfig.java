package cl.cokke.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import cl.cokke.security.JwtTokenFilterConfigurer;
import cl.cokke.security.JwtTokenProvider;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	 @Bean
	    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
		.antMatchers("/api/v1/auth/signin").permitAll()
		.antMatchers("/api/v1/auth/signup").permitAll()
		.antMatchers("/api/v1/auth/**").hasAnyRole("ADMIN")
		.antMatchers("/api/v1/disney/**").hasAnyRole("CLIENT")
		.anyRequest().authenticated();
		
		http.exceptionHandling().accessDeniedPage("/login");
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
		
		return http.build();
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
