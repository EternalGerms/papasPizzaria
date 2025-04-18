package br.com.papaspizzaria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.papaspizzaria.security.jwt.AuthEntryPointJwt;
import br.com.papaspizzaria.security.jwt.AuthFilterToken;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler; // Handler para tratar erros de autenticação
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Configura o encoder de senhas (BCrypt)
	} 
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AuthFilterToken authFilterToken() {
		return new AuthFilterToken(); // Filtro personalizado para validar tokens JWT
	}
	
	
	// Acesso e Restrição de requisições conforme ROLE, Autenticação ou Acesso Livre.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors(Customizer.withDefaults());
	    http.csrf(csrf -> csrf.disable())
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/auth/**").permitAll()
	                // Endpoints públicos
	                .requestMatchers(HttpMethod.GET, "/produtos").permitAll()
	                .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()
	                // Endpoints que exigem autenticação como FUNCIONARIO (admin)
	                .requestMatchers(HttpMethod.POST, "/produtos").hasRole("FUNCIONARIO")
	                .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("FUNCIONARIO")
	                .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("FUNCIONARIO")
	                // Outras configurações
	                .requestMatchers("/usuarios/testUser").hasRole("CLIENTE")
	                .requestMatchers("/usuarios/**").hasRole("FUNCIONARIO")
	                .anyRequest().authenticated());
	    
	    http.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}

}
