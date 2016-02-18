package edu.illinois.ugl.minrva.data;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DbConfig implements ServletContextListener {
	private final String ENVIRONMENT_URL = "minrva_db_url";
	private final String ENVIRONMENT_USERNAME = "minrva_db_username";
	private final String ENVIRONMENT_PASSWORD = "minrva_db_password";
	private static String url;
	private static String username;
	private static String password;

	public void contextInitialized(ServletContextEvent arg0) {
		url = System.getenv(ENVIRONMENT_URL);
		username = System.getenv(ENVIRONMENT_USERNAME);
		password = System.getenv(ENVIRONMENT_PASSWORD);

		String errorMessage = "DbConfig: Could not retrieve enviornment variable [%s]. It may not be set.";
		if (url == null) {
			System.err.println(String.format(errorMessage, ENVIRONMENT_URL));
		}
		if (username == null) {
			System.err.println(String.format(errorMessage, ENVIRONMENT_USERNAME));
		}
		if (password == null) {
			System.err.println(String.format(errorMessage, ENVIRONMENT_PASSWORD));
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}// end constextDestroyed method

	public static String getUrl() {
		return url;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}
}
