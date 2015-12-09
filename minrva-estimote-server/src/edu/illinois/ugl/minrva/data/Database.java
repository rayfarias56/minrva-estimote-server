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
	private static final String BEACONS_COL_DESCRIPTION = "description";
	
	private PreparedStatement createBeacon;
	private PreparedStatement updateBeacon;

	
	private Database() {
		boolean populated = false;
		
		try {
			Class.forName("org.sqlite.JDBC");
			File dbFile = new File(DbConfig.dbUrl);
			dbFile.delete(); // TODO: delete this line in final version
			populated = dbFile.exists();
			con = DriverManager.getConnection("jdbc:sqlite:" + DbConfig.dbUrl);
			s = con.createStatement();
			if (!populated)
				initTables();
			
			createBeacon = con.prepareStatement(String.format(
					"INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?);",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID,
					BEACONS_COL_MAJOR, BEACONS_COL_MINOR,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z,
					BEACONS_COL_DESCRIPTION));
			updateBeacon = con.prepareStatement(String.format(
					"UPDATE %s SET %s=?,  %s=?,  %s=?, %s=? "
					+ "WHERE  %s=? AND %s=? AND %s=?;",
					BEACONS_TABLE_NAME,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z, BEACONS_COL_DESCRIPTION,
					BEACONS_COL_UUID, BEACONS_COL_MAJOR, BEACONS_COL_MINOR));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		if (!populated) {
			initData();
		}
	}

	private void initTables() {
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
					+ "%s TEXT NOT NULL,"
					+ "PRIMARY KEY (%s, %s, %s)"
					+ ")",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID,
					BEACONS_COL_MAJOR, BEACONS_COL_MINOR,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z,
					BEACONS_COL_DESCRIPTION,
					BEACONS_COL_UUID, BEACONS_COL_MAJOR, BEACONS_COL_MINOR);
			s.execute(createBeaconTable);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initData() {
		System.out.println("Populating Database");
				
		// mock data to test
		String uuid = "c8236aad-c8bb-4a39-99dd-f48ed66d64fb";
		createBeacon(new Beacon(uuid, 21435, 14720, 00.34, 03.45, 12.54, "Under the east stairs"));
		createBeacon(new Beacon(uuid, 21435, 28029, 03.34, 03.86, 08.45, "On the top back corner of case 15"));
		createBeacon(new Beacon(uuid, 21435, 33798, 30.45, 05.35, 05.98, "Over the west entryway"));
		createBeacon(new Beacon(uuid, 50234, 53345, 00.23, 13.70, 46.70, "On the top front corner of case 7"));
		createBeacon(new Beacon(uuid, 50234, 23409, 03.65, 03.34, 03.34, "On the inner east wall of the courtyard"));
		
		uuid = "9efde5f4-e059-4240-93d0-2e9e2fcfb19b";
		createBeacon(new Beacon(uuid, 32344, 00234, 00.23, 54.34, 02.65, ""));
		createBeacon(new Beacon(uuid, 32344, 23042, 03.23, 03.99, 07.01, "On the second top shelf of case 3"));
		createBeacon(new Beacon(uuid, 32344, 12303, 22.54, 05.00, 05.00, "Above the ceiling tile in the southwest corner"));
		createBeacon(new Beacon(uuid, 32344, 44024, 11.23, 11.34, 11.87, ""));
		createBeacon(new Beacon(uuid, 02342, 12523, 00.00, 02.23, 11.11,""));
		createBeacon(new Beacon(uuid, 02342, 12424, 03.32, 03.53, 03.23, "Over the checkout desk"));
		createBeacon(new Beacon(uuid, 02342, 23490, 32.54, 05.22, 02.43, ""));
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
			String uuid = res.getString(BEACONS_COL_UUID);
			int major = res.getInt(BEACONS_COL_MAJOR);
			int minor = res.getInt(BEACONS_COL_MINOR);
			float x = res.getFloat(BEACONS_COL_X);
			float y = res.getFloat(BEACONS_COL_Y);
			float z = res.getFloat(BEACONS_COL_Z);
			String description = res.getString(BEACONS_COL_DESCRIPTION);
			b = new Beacon(uuid, major, minor, x, y, z, description);
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
	public List<Beacon> getBeacons(String uuid) {
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s='%s';",
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
	public List<Beacon> getBeacons(String uuid, int major) {
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s='%s' AND %s=%d;",
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
	public Beacon getBeacon(String uuid, int major, int minor) {
		Beacon beacon = null;
		// TODO ensure only one beacon is returned
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s='%s' AND %s=%d AND %s=%d;",
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
			createBeacon.setString(1, beacon.getUuid());
			createBeacon.setInt(2, beacon.getMajor());
			createBeacon.setInt(3, beacon.getMinor());
			createBeacon.setDouble(4, beacon.getX());
			createBeacon.setDouble(5, beacon.getY());
			createBeacon.setDouble(6, beacon.getZ());
			createBeacon.setString(7, beacon.getDescription());
			con.setAutoCommit(false);
			createBeacon.executeUpdate();
			s.execute(String.format("UPDATE %s SET %s = %s + 1;", VERSIONS_TABLE_NAME, VERSIONS_COL_ID, VERSIONS_COL_ID));
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateBeacon(Beacon beacon) {
		// TODO Decide if we want an update to Uuid, Major, Minor or delete/add
		System.out.println(beacon.getUuid());
		System.out.println(beacon.getMajor());
		System.out.println(beacon.getMinor());
		try {
			updateBeacon.setDouble(1, beacon.getX());
			updateBeacon.setDouble(2, beacon.getY());
			updateBeacon.setDouble(3, beacon.getZ());
			updateBeacon.setString(4, beacon.getDescription());
			updateBeacon.setString(5, beacon.getUuid());
			updateBeacon.setInt(6, beacon.getMajor());
			updateBeacon.setInt(7, beacon.getMinor());
			con.setAutoCommit(false);
			updateBeacon.executeUpdate();
			s.execute(String.format("UPDATE %s SET %s = %s + 1;", VERSIONS_TABLE_NAME, VERSIONS_COL_ID, VERSIONS_COL_ID));
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteBeacon(String uuid, int major, int minor) {
		try {
			String deleteBeacon = String.format(
					"DELETE FROM %s WHERE  %s='%s' AND %s=%d AND %s=%d;",
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
