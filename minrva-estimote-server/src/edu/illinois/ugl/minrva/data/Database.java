package edu.illinois.ugl.minrva.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64.Decoder;
import java.io.File;
import java.sql.*;

import edu.illinois.ugl.minrva.models.NewVersion;
import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.models.Beacon;

public enum Database implements VersionDao, BeaconDao {
	INSTANCE;

	Connection con;
	Statement s;
	
	private final String DB_NAME = "estimote";
	
	private static final String VERSIONS_TABLE_NAME = "Versions";
	private static final String VERSIONS_COL_ID = "id";
	private static final String VERSIONS_COL_IS_PRODUCTION = "isProduction";
	
	private static final String BEACONS_TABLE_NAME = "Beacons";
	private static final String BEACONS_COL_VERSION_ID = "versionId";
	private static final String BEACONS_COL_MAJOR = "major";
	private static final String BEACONS_COL_MINOR = "minor";
	private static final String BEACONS_COL_X = "x";
	private static final String BEACONS_COL_Y = "y";
	private static final String BEACONS_COL_Z = "z";

	
	private Database() {
		boolean populated = false;
		
		try {
			Class.forName("org.sqlite.JDBC");
			String dbPath = "D:/" + DB_NAME + ".db";
			populated = (new File(dbPath)).exists();
			con = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			s = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!populated)
			initData();
	}

	private void initData() {
		try {
			String createVersionTable = String.format(
					"CREATE TABLE %s ("
					+ "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "%s BOOLEAN NOT NULL"
					+ ")",
					VERSIONS_TABLE_NAME, VERSIONS_COL_ID, VERSIONS_COL_IS_PRODUCTION);
			s.execute(createVersionTable);
			
			// TODO: allow estimotes to be added without any location
			String createBeaconTable = String.format(
					"CREATE TABLE %s ("
					+ "%s INTEGER NOT NULL,"
					+ "%s INTEGER NOT NULL,"
					+ "%s INTEGER NOT NULL,"
					+ "%s REAL NOT NULL,"
					+ "%s REAL NOT NULL,"
					+ "%s REAL NOT NULL,"
					+ "FOREIGN KEY(%s) REFERENCES %s(%s),"
					+ "PRIMARY KEY (%s, %s, %s)"
					+ ")",
					BEACONS_TABLE_NAME, BEACONS_COL_VERSION_ID,
					BEACONS_COL_MAJOR, BEACONS_COL_MINOR,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z,
					BEACONS_COL_VERSION_ID, VERSIONS_TABLE_NAME, VERSIONS_COL_ID,
					BEACONS_COL_VERSION_ID, BEACONS_COL_MAJOR, BEACONS_COL_MINOR);
			s.execute(createBeaconTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		// no beacons in production to begin to begin
		createVersion(new NewVersion(true, new ArrayList<Beacon>()));
		
		// mock data to test
		
		List<Beacon> beacons = new ArrayList<Beacon>();
		beacons.add(new Beacon(11, 111, 0, 0, 0));
		createVersion(new NewVersion(false, beacons));
		
		beacons = new ArrayList<Beacon>();
		beacons.add(new Beacon(11, 111, 0, 0, 0));
		beacons.add(new Beacon(11, 112, 0, 10, 0));
		beacons.add(new Beacon(11, 113, 10, 0, 0));
		beacons.add(new Beacon(11, 114, 10, 10, 0));
		createVersion(new NewVersion(false, beacons));
		
		beacons = new ArrayList<Beacon>();
		beacons.add(new Beacon(11, 111, 5, 5, 0));
		beacons.add(new Beacon(11, 112, 0, 10, 0));
		beacons.add(new Beacon(11, 113, 10, 0, 0));
		beacons.add(new Beacon(11, 114, 15, 15, 0));
		beacons.add(new Beacon(12, 111, 15, 15, 0));
		beacons.add(new Beacon(12, 112, 15, 15, 10));
		beacons.add(new Beacon(12, 113, 3, 3, 3));
		createVersion(new NewVersion(true, beacons));
		
		beacons = new ArrayList<Beacon>();
		beacons.add(new Beacon(12, 111, 15, 15, 0));
		beacons.add(new Beacon(12, 112, 15, 15, 10));
		beacons.add(new Beacon(12, 113, 3, 3, 3));
		beacons.add(new Beacon(13, 111, 1, 1, 1));
		createVersion(new NewVersion(false, beacons));
	}
			
	private Version decodeVersion(ResultSet res) {
		Version v = null;
		
		try {
			long id = res.getLong(VERSIONS_COL_ID);
			boolean isProduction = res.getBoolean(VERSIONS_COL_IS_PRODUCTION);
			v = new Version(id, isProduction, getBeacons(id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return v;
	}
	
	private Beacon decodeBeacon(ResultSet res) {
		Beacon b = null;
		
		try {
			int major = res.getInt(BEACONS_COL_MAJOR);
			int minor = res.getInt(BEACONS_COL_MINOR);
			float x = res.getFloat(BEACONS_COL_X);
			float y = res.getFloat(BEACONS_COL_Y);
			float z = res.getFloat(BEACONS_COL_Z);
			b = new Beacon(major, minor, x, y, z);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}

	
	@Override
	public List<Version> getVersions() {
		List<Version> list = new ArrayList<Version>();
		
		try {
			String getVersions = String.format("SELECT * FROM %s", VERSIONS_TABLE_NAME);
			ResultSet res = s.executeQuery(getVersions);
			while (res.next())
				list.add(decodeVersion(res));
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Version getVersion(long id) {
		Version v = null;
		
		try {
			String getVersion = String.format("SELECT * FROM %s WHERE %s=%d",
					VERSIONS_TABLE_NAME, VERSIONS_COL_ID, id);
			ResultSet res = s.executeQuery(getVersion);
			v = decodeVersion(res);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return v;
	}

	@Override
	public Version getProductionVersion() {
		Version v = null;
		
		try {
			String getProductionVersion = String.format(
					"SELECT * FROM %s WHERE %s=1 ORDER BY %s DESC LIMIT 1",
					VERSIONS_TABLE_NAME, VERSIONS_COL_IS_PRODUCTION, VERSIONS_COL_ID);
			ResultSet res = s.executeQuery(getProductionVersion);
			v = decodeVersion(res);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return v;
	}

	@Override
	public long createVersion(NewVersion version) {
		long id = -1;
		
		try {
			int isProduction = version.isProduction() ? 1 : 0;
			String createVersion = String.format(
					"INSERT INTO %s (%s) VALUES (%d)",
					VERSIONS_TABLE_NAME, VERSIONS_COL_IS_PRODUCTION, isProduction);
			s.execute(createVersion);
			Statement s = con.createStatement();
			ResultSet res = s.executeQuery("SELECT last_insert_rowid()");
			res.next();
			id = res.getLong(1);
			s.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Beacon beacon : version.getBeacons())
			createBeacon(id, beacon);
		
		return id;
	}

	@Override
	public List<Beacon> getBeacons(long versionId) {
		List<Beacon> list = new ArrayList<Beacon>();
		
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s=%d",
					BEACONS_TABLE_NAME, BEACONS_COL_VERSION_ID, versionId);
			ResultSet res = s.executeQuery(getBeacons);
			while (res.next())
				list.add(decodeBeacon(res));
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<Beacon> getBeacons(long versionId, int major) {
		List<Beacon> list = new ArrayList<Beacon>();
		
		try {
			String getBeacons = String.format(
					"SELECT * FROM %s WHERE %s=%d AND %s=%d",
					BEACONS_TABLE_NAME, BEACONS_COL_VERSION_ID, versionId,
					BEACONS_COL_MAJOR, major);
			ResultSet res = s.executeQuery(getBeacons);
			while (res.next())
				list.add(decodeBeacon(res));
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Beacon getBeacon(long versionId, int major, int minor) {
		Beacon b = null;
		
		try {
			String getBeacon = String.format(
					"SELECT * FROM %s WHERE %s=%d AND %s=%d AND %s=%d",
					BEACONS_TABLE_NAME, BEACONS_COL_VERSION_ID, versionId,
					BEACONS_COL_MAJOR, major, BEACONS_COL_MINOR, minor);
			ResultSet res = s.executeQuery(getBeacon);
			b = decodeBeacon(res);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}

	@Override
	public void createBeacon(long versionId, Beacon beacon) {		
		try {
			String createBeacon = String.format(
					"INSERT INTO %s (%s,%s,%s,%s,%s,%s) VALUES (%d,%d,%d,%f,%f,%f)",
					BEACONS_TABLE_NAME, BEACONS_COL_VERSION_ID,
					BEACONS_COL_MAJOR, BEACONS_COL_MINOR,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z,
					versionId, beacon.getMajor(), beacon.getMinor(),
					beacon.getX(), beacon.getY(), beacon.getZ());
			s.execute(createBeacon);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}