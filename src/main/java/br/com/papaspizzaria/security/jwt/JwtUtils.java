package br.com.papaspizzaria.security.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.papaspizzaria.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	@Value("${papapizzaria.jwtSecret}")
	private String jwtSecret;

	@Value("${papapizzaria.jwtExpirationMs}")
	private int jwtExpirationMs;

	// TODO Atualizar código para não usar de métodos/funções deprecated
	public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {
		return Jwts.builder().setSubject(userDetail.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(getSigninKey(), SignatureAlgorithm.HS512).compact();
	}

	public Key getSigninKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		return key;
	}
	
	public String getLoginToken(String token) {
		return Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			System.out.println("Token Inválido: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("Token não suportado: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.out.println("Token Inválido: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Token com argumento inválido: " + e.getMessage());
		}
		return false;
	}

}
