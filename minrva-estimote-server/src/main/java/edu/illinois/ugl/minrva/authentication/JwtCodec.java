package edu.illinois.ugl.minrva.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;

public class JwtCodec {

	public static final SignatureAlgorithm ALGORITHM_SIGNATURE = SignatureAlgorithm.HS512;
	public static final String ALGORITHM_SIGNATURE_STRING = ALGORITHM_SIGNATURE.getJcaName();

	private static final String ISSUER = "Minrva-Wayfinder";
	private static final long EXPIRATION_MILLIS = 300000; // 5 mins  // 24 hours = 86400000
	private static final String SUBJECT = "Wayfinder Authentication";

	public static String createJwt(String id) {

		// Create issued at date.
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// Create expieration date.
		long expMillis = nowMillis + EXPIRATION_MILLIS;
		Date exp = new Date(expMillis);

		// Set JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(SUBJECT)
				.setIssuer(ISSUER).setExpiration(exp)
				.signWith(ALGORITHM_SIGNATURE, KeyConfig.getKey());

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	private static Claims verifyJwt(String jwt) {
		try {
			return Jwts.parser().requireSubject(SUBJECT).requireIssuer(ISSUER)
					.setSigningKey(KeyConfig.getKey()).parseClaimsJws(jwt).getBody();
		} catch (SignatureException se) {
			se.printStackTrace();
			System.err.println("JWT Token: Potentially malicious login attempt.");
		} catch (MissingClaimException mce) {
			mce.printStackTrace();
		} catch (IncorrectClaimException ice) {
			ice.printStackTrace();
		}
		return null;
	}

	public static boolean isValidJwt(String jwt) {
		Claims trustedClaims = verifyJwt(jwt);
		long nowMillis = System.currentTimeMillis();

		try {
			if (nowMillis < trustedClaims.getExpiration().getTime()) {
				return true;
			}
		} catch (MissingClaimException mce) {
			mce.printStackTrace();
		}

		return false;
	}

}