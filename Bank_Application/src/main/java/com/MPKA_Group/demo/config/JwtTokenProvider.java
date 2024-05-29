package com.MPKA_Group.demo.config;

import java.security.Key;
import java.util.Date;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration}")
	private long jwtExpirationDate; 
	
	public String generateToken(org.springframework.security.core.Authentication authentication)
	{
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(Key())
				.compact();
	}

	private Key Key()
	{
		byte[] bytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(bytes);
	}
	
	public String getUsername(String token)
	{
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(Key())
				.build()
				.parseClaimsjws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	
	public boolean validateToken(String token)
	{
		try
		{
			Jwts.parserBuilder()
			.setSigningKey(Key())
			.build()
			.parse(token);
		return true;
		}
		catch(ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e)
		{
			throw new RuntimeException(e);
		}
	
	}
}
