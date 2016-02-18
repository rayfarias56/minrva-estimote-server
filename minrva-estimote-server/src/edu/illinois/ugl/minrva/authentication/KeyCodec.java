package edu.illinois.ugl.minrva.authentication;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyCodec {
	
	public static String encodeKey(SecretKey key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static SecretKey decodeKey(String encodedKey) {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, 
				JwtCodec.ALGORITHM_SIGNATURE_STRING);
	}

}
