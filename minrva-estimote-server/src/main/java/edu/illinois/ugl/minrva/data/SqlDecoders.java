package edu.illinois.ugl.minrva.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.models.User;
import edu.illinois.ugl.minrva.models.Version;

public class SqlDecoders {

	static User decodeUser(ResultSet res) {
		User u = null;
		
		try {
			int id = res.getInt(Constants.USERS_COL_ID);
			String username= res.getString(Constants.USERS_COL_USERNAME);
			String password = res.getString(Constants.USERS_COL_PASSWORD);
			
			u = new User(id, username, password);
			
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
		}
		
		return u;
		
	}

	static Version decodeVersion(ResultSet res) {
		Version v = null;

		try {
			int id = res.getInt(Constants.VERSIONS_COL_ID);
			v = new Version(Long.valueOf(id));
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
		}

		return v;
	}

	static Beacon decodeBeacon(ResultSet res) {
		Beacon b = null;

		try {
			String uuid = res.getString(Constants.BEACONS_COL_UUID);
			int major = res.getInt(Constants.BEACONS_COL_MAJOR);
			int minor = res.getInt(Constants.BEACONS_COL_MINOR);
			float x = res.getFloat(Constants.BEACONS_COL_X);
			float y = res.getFloat(Constants.BEACONS_COL_Y);
			float z = res.getFloat(Constants.BEACONS_COL_Z);
			String description = res.getString(Constants.BEACONS_COL_DESCRIPTION);
			b = new Beacon(uuid, major, minor, x, y, z, description);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return b;
	}
}
