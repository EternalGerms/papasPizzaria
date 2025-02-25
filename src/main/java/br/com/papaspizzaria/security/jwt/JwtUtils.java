package br.com.papaspizzaria.security.jwt;

import java.nio.charset.MalformedInputException;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.papaspizzaria.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	@Value("${papapizzaria.jwtSecret}")
	private String jwtSecret;
	
	@Value("${papapizzaria.jweExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {
		return Jwts.builder().setSubject(userDetail.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(getSigninKey(),SignatureAlgorithm.ES512).compact();
	}
	
	public Key getSigninKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		return key;
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(authToken);
		} catch(MalformedInputException e) {
			System.out.println("Token Inválido: " + e.getMessage());
		} catch(Exp) {
			System.out.println("Token Inválido: " + e.getMessage());
		}
	}

}
