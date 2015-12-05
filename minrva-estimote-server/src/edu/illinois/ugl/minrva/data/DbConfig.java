package edu.illinois.ugl.minrva.data;

import java.io.File;
import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DbConfig implements ServletContextListener {
	
	static String dbUrl;

	public void contextInitialized(ServletContextEvent arg0) {
		// Machine independent way to get the dbURI
		dbUrl = Paths.get(arg0.getServletContext().getRealPath(File.separator), "/WEB-INF/minrva.db").toString();
		System.out.println("The dburl would be: " + dbUrl);
		
	}// end contextInitialized method

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}// end constextDestroyed method

}
