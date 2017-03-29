package rs.ac.uns.ftn.informatika.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import rs.ac.uns.ftn.informatika.model.security.JwtUser;

public class JwtTokenUtil {

	public static final String CLAIM_KEY_USERNAME = "sub";
	public static final String CLAIM_KEY_CREATED = "created";
	public static final String CLAIM_KEY_AUTHORITIES = "authorities";
	
	public static final String JWT_SECRET = "secret";
	private static final int EXPIRATION_TIME_IN_SECONDS = 36000;

	
	public static String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(generateExpirationDate((Date) claims.get(CLAIM_KEY_CREATED)))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}

	public static Date generateExpirationDate(Date dateCreated) {
		return new Date(dateCreated.getTime() + EXPIRATION_TIME_IN_SECONDS * 1000);
	}

	public static Claims getClaimsFromToken(String token) {
		Claims claims;
		
		try {
			claims = Jwts.parser()
					.setSigningKey(JWT_SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			// TODO: log exception
			claims = null;
		}
		
		return claims;
	}

	public static String getUsernameFromToken(String token) {
		String username;
		
		try {
			Claims claimsFromToken = getClaimsFromToken(token);
			username = claimsFromToken.getSubject();
		} catch (Exception e) {
			// TODO: handle exception
			username = null;
		}
		
		return username;
	}

	public static Date getExpirationDateFromToken(String token) {
		Claims claimsFromToken = getClaimsFromToken(token);
		
		return claimsFromToken.getExpiration();
	}

	public static String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, generateExpirationDate(new Date()));
		claims.put(CLAIM_KEY_AUTHORITIES, userDetails.getAuthorities());
		
		return generateToken(claims);
	}

	public static boolean isValidToken(String authToken, UserDetails userDetails) {
		JwtUser jwtUser = (JwtUser) userDetails;
		String usernameFromToken = getUsernameFromToken(authToken);
		
		// TODO: Extend a token validation with the additional checks (isExpired, isCreatedBeforeLastPasswordReset)
		return usernameFromToken.equals(jwtUser.getUsername());
	}

	public static final String TOKEN_HEADER = "Authorization";


	public static String getTokenFromHttpHeader(String tokenHeader) {
		String token = "";
		
		if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
			token = tokenHeader.substring(7);
		}
		return token;
	}

}
