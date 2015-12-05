package edu.illinois.ugl.minrva.data;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.sql.*;

import edu.illinois.ugl.minrva.data.DbConfig;

import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.models.Beacon;


public enum Database implements VersionDao, BeaconDao {
	INSTANCE;

	// TODO Should be opening and closing statements more often
	// TODO Should maybe used prepared statements to avoid sql injections
	// TODO Error checking and all that other proper stuff.
	// TODO Execute increments as transactions
	Connection con;
	Statement s;
	
	private static final String VERSIONS_TABLE_NAME = "Versions";
	private static final String VERSIONS_COL_ID = "id";
	
	private static final String BEACONS_TABLE_NAME = "Beacons";
	private static final String BEACONS_COL_UUID = "uuid";
	private static final String BEACONS_COL_MAJOR = "major";
	private static final String BEACONS_COL_MINOR = "minor";
	private static final String BEACONS_COL_X = "x";
	private static final String BEACONS_COL_Y = "y";
	private static final String BEACONS_COL_Z = "z";

	
	private Database() {
		boolean populated = false;
		
		try {
			Class.forName("org.sqlite.JDBC");
			populated = (new File(DbConfig.dbUrl)).exists();
			con = DriverManager.getConnection("jdbc:sqlite:" + DbConfig.dbUrl);
			s = con.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (!populated) {
			initData();
		}		
	}

	private void initData() {
		System.out.println("Populating Database");
		try {
			String createVersionTable = String.format(
					"CREATE TABLE %s (%s INTEGER PRIMARY KEY);",
					VERSIONS_TABLE_NAME, VERSIONS_COL_ID, VERSIONS_TABLE_NAME, 1);
			s.execute(createVersionTable);
			s.execute(String.format("INSERT INTO %s VALUES (%d);", VERSIONS_TABLE_NAME, 1));
			
			// TODO: allow estimotes to be added without any location
			String createBeaconTable = String.format(
					"CREATE TABLE %s ("
					+ "%s INTEGER NOT NULL,"
					+ "%s INTEGER NOT NULL,"
					+ "%s INTEGER NOT NULL,"
					+ "%s REAL NOT NULL,"
					+ "%s REAL NOT NULL,"
					+ "%s REAL NOT NULL,"
					+ "PRIMARY KEY (%s, %s, %s)"
					+ ")",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID,
					BEACONS_COL_MAJOR, BEACONS_COL_MINOR,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z,
					BEACONS_COL_UUID, BEACONS_COL_MAJOR, BEACONS_COL_MINOR);
			s.execute(createBeaconTable);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		// mock data to test
		createBeacon(new Beacon(/*uuid*/ 11,/*major*/ 2,/*minor*/ 1,/*x*/ 0,/*y*/ 0,/*z*/0));
		createBeacon(new Beacon(/*uuid*/ 11,/*major*/ 2,/*minor*/ 2,/*x*/ 3,/*y*/ 3,/*z*/3));
		createBeacon(new Beacon(/*uuid*/ 11,/*major*/ 2,/*minor*/ 3,/*x*/ 5,/*y*/ 5,/*z*/5));
		createBeacon(new Beacon(/*uuid*/ 11,/*major*/ 5,/*minor*/ 56,/*x*/ 0,/*y*/ 0,/*z*/0));
		createBeacon(new Beacon(/*uuid*/ 11,/*major*/ 5,/*minor*/ 57,/*x*/ 3,/*y*/ 3,/*z*/3));
		
		createBeacon(new Beacon(/*uuid*/ 12,/*major*/ 3,/*minor*/ 1,/*x*/ 0,/*y*/ 0,/*z*/0));
		createBeacon(new Beacon(/*uuid*/ 12,/*major*/ 3,/*minor*/ 3,/*x*/ 3,/*y*/ 3,/*z*/3));
		createBeacon(new Beacon(/*uuid*/ 12,/*major*/ 3,/*minor*/ 5,/*x*/ 5,/*y*/ 5,/*z*/5));
		createBeacon(new Beacon(/*uuid*/ 12,/*major*/ 3,/*minor*/ 7,/*x*/ 11,/*y*/ 11,/*z*/11));
		createBeacon(new Beacon(/*uuid*/ 12,/*major*/ 5,/*minor*/ 1,/*x*/ 0,/*y*/ 0,/*z*/0));
		createBeacon(new Beacon(/*uuid*/ 12,/*major*/ 5,/*minor*/ 3,/*x*/ 3,/*y*/ 3,/*z*/3));
		createBeacon(new Beacon(/*uuid*/ 12,/*major*/ 5,/*minor*/ 5,/*x*/ 5,/*y*/ 5,/*z*/5));
	}
			
	private Version decodeVersion(ResultSet res) {
		Version v = null;
		
		try {
			long id = res.getLong(VERSIONS_COL_ID);
			v = new Version(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return v;
	}
	
	private Beacon decodeBeacon(ResultSet res) {
		Beacon b = null;
		
		try {
			int uuid = res.getInt(BEACONS_COL_UUID);
			int major = res.getInt(BEACONS_COL_MAJOR);
			int minor = res.getInt(BEACONS_COL_MINOR);
			float x = res.getFloat(BEACONS_COL_X);
			float y = res.getFloat(BEACONS_COL_Y);
			float z = res.getFloat(BEACONS_COL_Z);
			b = new Beacon(uuid, major, minor, x, y, z);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}

	@Override
	public List<Beacon> getBeacons() {
		
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s;",
					BEACONS_TABLE_NAME);
			ResultSet res = s.executeQuery(getBeacons);
			while (res.next()) {
				list.add(decodeBeacon(res));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<Beacon> getBeacons(int uuid) {
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s=%d;",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID, uuid);
			ResultSet res = s.executeQuery(getBeacons);
			while (res.next()) {
				list.add(decodeBeacon(res));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<Beacon> getBeacons(int uuid, int major) {
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s=%d AND %s=%d;",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID, uuid,
					BEACONS_COL_MAJOR, major);
			ResultSet res = s.executeQuery(getBeacons);
			while (res.next()) {
				list.add(decodeBeacon(res));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Beacon getBeacon(int uuid, int major, int minor) {
		Beacon beacon = null;
		// TODO ensure only one beacon is returned
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s=%d AND %s=%d AND %s=%d;",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID, uuid,
					BEACONS_COL_MAJOR, major, BEACONS_COL_MINOR, minor);
			ResultSet res = s.executeQuery(getBeacons);
			beacon = decodeBeacon(res);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return beacon;
	}

	@Override
	public void createBeacon(Beacon beacon) {
		try {
			String createBeacon = String.format(
					"INSERT INTO %s (%s,%s,%s,%s,%s,%s) VALUES (%d,%d,%d,%f,%f,%f);",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID,
					BEACONS_COL_MAJOR, BEACONS_COL_MINOR,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z,
					beacon.getUuid(), beacon.getMajor(), beacon.getMinor(),
					beacon.getX(), beacon.getY(), beacon.getZ());
			s.execute(createBeacon);
			s.execute(String.format("UPDATE %s SET %s = %s + 1;", VERSIONS_TABLE_NAME, VERSIONS_COL_ID, VERSIONS_COL_ID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateBeacon(Beacon beacon) {
		// TODO Decide if we want an update to Uuid, Major, Minor or delete/add
		try {
			String updateBeacon = String.format(
					"UPDATE %s SET %s=%f,  %s=%f,  %s=%f "
					+ "WHERE  %s=%d AND %s=%d AND %s=%d;",
					BEACONS_TABLE_NAME, BEACONS_COL_X, beacon.getX(),
					BEACONS_COL_Y, beacon.getY(), BEACONS_COL_Z, beacon.getZ(),
					BEACONS_COL_UUID, beacon.getUuid(), BEACONS_COL_MAJOR, 
					beacon.getMajor(), BEACONS_COL_MINOR,  beacon.getMinor());
			s.execute(updateBeacon);
			s.execute(String.format("UPDATE %s SET %s = %s + 1;", VERSIONS_TABLE_NAME, VERSIONS_COL_ID, VERSIONS_COL_ID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteBeacon(int uuid, int major, int minor) {
		try {
			String deleteBeacon = String.format(
					"DELETE FROM %s WHERE  %s=%d AND %s=%d AND %s=%d;",
					BEACONS_TABLE_NAME,
					BEACONS_COL_UUID, uuid, BEACONS_COL_MAJOR, 
					major, BEACONS_COL_MINOR, minor);
			s.execute(deleteBeacon);
			s.execute(String.format("UPDATE %s SET %s = %s + 1;", VERSIONS_TABLE_NAME, VERSIONS_COL_ID, VERSIONS_COL_ID));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public Version getVersion() {
		Version version = null;
		// TODO Ensure only one version ever exists
		try {
			String getVersion = String.format("SELECT * FROM %s;", VERSIONS_TABLE_NAME);
			ResultSet res = s.executeQuery(getVersion);
			version = decodeVersion(res);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return version;
	}
}
