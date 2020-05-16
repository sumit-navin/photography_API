package com.navigraph.photobook.util;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navigraph.photobook.model.JwtPayload;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	private static final Integer JWT_EXPIRY = 1000 * 60 * 60 * 10;
	private static final Decoder base64decoder = Base64.getDecoder();

	@Autowired
	private ObjectMapper objectMapper;

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> map = new HashMap<>();
		return createToken(map, userDetails.getUsername(), userDetails.getPassword());
	}

	private String createToken(Map<String, Object> claims, String subject, String key) {
		Long now = System.currentTimeMillis();
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(now))
				.setExpiration(new Date(now + JWT_EXPIRY)).signWith(SignatureAlgorithm.HS256, key).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token, userDetails.getPassword());
		return username != null && username.equals(userDetails.getUsername())
				&& !isTokenExpired(token, userDetails.getPassword());

	}

	public boolean isTokenExpired(String token, String key) {
		Date expiration = extractClaim(token, Claims::getExpiration, key);
		return expiration != null && expiration.before(new Date());
	}

	public String extractUsername(String token, String key) {
		return extractClaim(token, Claims::getSubject, key);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String key) {
		final Claims claims = extractAllClaims(token, key);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token, String key) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}

	public String extractUserNameWithoutKey(String token) {
		String[] tokenParts = token.split("\\.");
		JwtPayload jwtPayload = null;
		try {
			jwtPayload = objectMapper.readValue(base64decoder.decode(tokenParts[1]), JwtPayload.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jwtPayload.getSub();
	}
}
