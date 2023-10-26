package com.bikkadit.bolg.security;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.xml.crypto.Data;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bikkadit.bolg.constants.AppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	
	private String secret="jwtTokenKey";
	
	// retrive username form jwt token
	
	public String getUsernameFromToken(String token) {
		return getclaimFromToken(token,Claims::getSubject);
		
	}
	
	// retrive expiration date from jwt token
	
	public Date gtExpriationDateFromToken(String token) {
		
		return getclaimFromToken(token,Claims::getExpiration);
		
	}
	
	public <T> T getclaimFromToken(String token,Function<Claims, T>claimsResolver) {
		final Claims claims=getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
	}
	
	// check token expired or not
	
	private Boolean isTokenExpired(String token) {
		final Date expiration=gtExpriationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	
	// generate token for user
	
	public String genetateToken(UserDetails userDetails) {
		Map<String, Object> claims=new HashMap<>();
		return doGenerateToken(claims,userDetails.getUsername());
	}
	
	
	private String doGenerateToken(Map<String, Object> claims,String subject) {
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+AppConstants.JWT_TOKEN_VALIDITY*100))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		
	}
	
	// validate token
	public Boolean validateToken(String token,UserDetails userDetails) {
		final String username=getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
