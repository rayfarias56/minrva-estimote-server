package edu.illinois.ugl.minrva.application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import org.glassfish.jersey.server.ResourceConfig;

import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.data.UserDao;
import edu.illinois.ugl.minrva.data.VersionDao;

/**
 * This is the Jersey sttartup point for the projected. It's specified in the <tt>web.xml</tt> file,
 * and is used when Jersey is loaded to determine where Resources are located and to register any
 * binders.
 */
public class WayfinderApplication extends ResourceConfig {
	
	/**
	 * This is the constructor called by the application server when loading the application.
	 */
	public WayfinderApplication() {	
		this(Database.INSTANCE);
	}
	
	/**
	 * This is the constructor called directly by unit tests. This allows us to pass in a mocked
	 * version of the dependencies. 
	 */
	public WayfinderApplication(final Database database) {
		
		// Bind BeaconDao
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(database).to(BeaconDao.class);
			}
		});
		
		// Bind VersionDao
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(database).to(VersionDao.class);
			}
		});
		
		// Bind UserDao
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(database).to(UserDao.class);
			}
		});	

		// Specify where resource classes are located. 
		packages("edu.illinois.ugl.minrva.resources", "com.fasterxml.jackson.jaxrs.json");
	}
}
