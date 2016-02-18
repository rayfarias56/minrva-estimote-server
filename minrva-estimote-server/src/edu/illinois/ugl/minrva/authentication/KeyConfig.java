package edu.illinois.ugl.minrva.authentication;

import javax.crypto.SecretKey;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import io.jsonwebtoken.impl.crypto.MacProvider;

public class KeyConfig implements ServletContextListener {
	private final String ENVIRONMENT_KEY_NAME = "minrva_signing_key";
	private static SecretKey key;
	
	

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String encodedKey = System.getenv(ENVIRONMENT_KEY_NAME);
		
		if (encodedKey == null) {
			System.err.println(
					"KeyConfig: Could not retrieve the enviornment variable [minrva_signing_key]."
							+ "It may not be set.");
		} else {
			key = KeyCodec.decodeKey(encodedKey);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}// end constextDestroyed method

	public static SecretKey getKey() {
		return key;
	}
}