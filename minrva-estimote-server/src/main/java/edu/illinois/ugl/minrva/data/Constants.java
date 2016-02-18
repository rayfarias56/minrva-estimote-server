package edu.illinois.ugl.minrva.data;

public class Constants {
	
	// Minrva Sql Database Constants
	static final String BEACONS_TABLE_NAME = "Beacons";
	static final String BEACONS_COL_UUID = "uuid";
	static final String BEACONS_COL_MAJOR = "major";
	static final String BEACONS_COL_MINOR = "minor";
	static final String BEACONS_COL_X = "x";
	static final String BEACONS_COL_Y = "y";
	static final String BEACONS_COL_Z = "z";
	static final String BEACONS_COL_DESCRIPTION = "description";
	
	static final String INCREMENT_STORED_PROC_SQL = "{call incrementVersion}";
	
	static final String USERS_TABLE_NAME = "Users";
	static final String USERS_COL_ID = "id";
	static final String USERS_COL_USERNAME = "username";
	static final String USERS_COL_PASSWORD = "password";
	
	static final String VERSIONS_TABLE_NAME = "Versions";
	static final String VERSIONS_COL_ID = "id";

}
