package edu.illinois.ugl.minrva.authentication;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;  


public class JwtCodec {
	
	public static final SignatureAlgorithm ALGORITHM_SIGNATURE = SignatureAlgorithm.HS512;
	public static final String ALGORITHM_SIGNATURE_STRING = ALGORITHM_SIGNATURE.getJcaName();
	
	public static final String JWT_ISSUER = "Minrva-Wayfinder"; 
	
	
	public static String createJwt(String id, String subject, long expireMillis) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// Set JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id)
		                                .setIssuedAt(now)
		                                .setSubject(subject)
		                                .setIssuer(JWT_ISSUER)
		                                .signWith(ALGORITHM_SIGNATURE, KeyConfig.getKey());

		 //if it has been specified, let's add the expiration
		if (expireMillis >= 0) {
		    long expMillis = nowMillis + expireMillis;
		    Date exp = new Date(expMillis);
		    builder.setExpiration(exp);
		}

		 //Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

}