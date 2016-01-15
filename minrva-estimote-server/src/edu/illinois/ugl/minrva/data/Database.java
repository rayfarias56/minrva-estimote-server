package edu.illinois.ugl.minrva.data;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import edu.illinois.ugl.minrva.data.DbConfig;
import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.models.Beacon;

import org.apache.commons.dbutils.DbUtils;


public enum Database implements VersionDao, BeaconDao {
	INSTANCE;
	
	// TODO There's probably a way to make this class more 'D.R.Y.'
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
	
	private static final String INCREMENT_STORED_PROC_SQL = "{call incrementVersion}";
	
	private Connection getConnection() {
		Connection con = null;	
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DbConfig.getUrl(), DbConfig.getUsername(), DbConfig.getPassword());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}

	// TODO Decoders should be moved out to a helper class. 
	private Version decodeVersion(ResultSet res) {
		Version v = null;

		try {
			int id = res.getInt(VERSIONS_COL_ID);
			v = new Version(Long.valueOf(id));
		} catch (SQLException e) {
			printSQLException(e);
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
		Connection con = getConnection();
		Statement s = null;
		ResultSet rs = null;
		List<Beacon> list = new ArrayList<Beacon>();
	
		try {
			String getBeacons = String.format("SELECT * FROM %s;", BEACONS_TABLE_NAME);
			s = con.createStatement();
			rs = s.executeQuery(getBeacons);
			while (rs.next()) {
				list.add(decodeBeacon(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(s);
		    DbUtils.closeQuietly(con);
		}
		
		return list;
	}

	@Override
	public List<Beacon> getBeacons(String uuid) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			ps = con.prepareStatement(String.format("SELECT * FROM %s WHERE %s=?;", BEACONS_TABLE_NAME, BEACONS_COL_UUID));
			ps.setString(1, uuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(decodeBeacon(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		    DbUtils.closeQuietly(con);
		}
		
		return list;
	}

	@Override
	public List<Beacon> getBeacons(String uuid, int major) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			ps = con.prepareStatement(String.format("SELECT * FROM %s WHERE %s=? AND %s=?;",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID, BEACONS_COL_MAJOR));
			ps.setString(1, uuid);
			ps.setInt(2, major);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(decodeBeacon(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		    DbUtils.closeQuietly(con);
		}
		
		return list;
	}

	@Override
	public Beacon getBeacon(String uuid, int major, int minor) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Beacon beacon = null;
		// TODO ensure only one beacon is returned
		try {
			ps = con.prepareStatement(String.format("SELECT * FROM %s WHERE %s=? AND %s=? AND %s=?;",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID, BEACONS_COL_MAJOR, BEACONS_COL_MINOR));
			ps.setString(1, uuid);
			ps.setInt(2, major);
			ps.setInt(3, minor);
			rs = ps.executeQuery();
			rs.next(); // Move to the first row
			beacon = decodeBeacon(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(ps);
		    DbUtils.closeQuietly(con);
		}
		
		return beacon;
	}
    
	@Override
	public void createBeacon(Beacon beacon) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		CallableStatement cs = null;
		try {
			con.setAutoCommit(false);
			
			cs = con.prepareCall(INCREMENT_STORED_PROC_SQL);
			ps = con.prepareStatement(String.format(
					"INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?);",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID,
					BEACONS_COL_MAJOR, BEACONS_COL_MINOR,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z,
					BEACONS_COL_DESCRIPTION));
			ps.setString(1, beacon.getUuid());
			ps.setInt(2, beacon.getMajor());
			ps.setInt(3, beacon.getMinor());
			ps.setDouble(4, beacon.getX());
			ps.setDouble(5, beacon.getY());
			ps.setDouble(6, beacon.getZ());
			ps.setString(7, beacon.getDescription());
			
			ps.executeUpdate();
			cs.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
	            try {
	                con.rollback();
	            } catch(SQLException excep) {
	            	e.printStackTrace();
	            }
	        }
		} finally {
		    DbUtils.closeQuietly(ps);
		    DbUtils.closeQuietly(con);
		}
	}

	@Override
	public void updateBeacon(Beacon beacon) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		CallableStatement cs = null;
		try {
			con.setAutoCommit(false);

			cs = con.prepareCall(INCREMENT_STORED_PROC_SQL);
			ps = con.prepareStatement(String.format(
					"UPDATE %s SET %s=?,  %s=?,  %s=?, %s=? "
					+ "WHERE  %s=? AND %s=? AND %s=?;",
					BEACONS_TABLE_NAME,
					BEACONS_COL_X, BEACONS_COL_Y, BEACONS_COL_Z, BEACONS_COL_DESCRIPTION,
					BEACONS_COL_UUID, BEACONS_COL_MAJOR, BEACONS_COL_MINOR));
			ps.setDouble(1, beacon.getX());
			ps.setDouble(2, beacon.getY());
			ps.setDouble(3, beacon.getZ());
			ps.setString(4, beacon.getDescription());
			ps.setString(5, beacon.getUuid());
			ps.setInt(6, beacon.getMajor());
			ps.setInt(7, beacon.getMinor());
			
			
			ps.executeUpdate();
			cs.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			if (con != null) {
	            try {
	                con.rollback();
	            } catch(SQLException excep) {
	            	e.printStackTrace();
	            }
	        }
		} finally {
		    DbUtils.closeQuietly(ps);
		    DbUtils.closeQuietly(con);
		}
	}

	@Override
	public void deleteBeacon(String uuid, int major, int minor) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		CallableStatement cs = null;
		try {
			con.setAutoCommit(false);
			
			cs = con.prepareCall(INCREMENT_STORED_PROC_SQL);
			ps = con.prepareStatement(String.format("DELETE FROM %s WHERE  %s=? AND %s=? AND %s=?;",
					BEACONS_TABLE_NAME, BEACONS_COL_UUID, BEACONS_COL_MAJOR, BEACONS_COL_MINOR));
			ps.setString(1, uuid);
			ps.setInt(2, major);
			ps.setInt(3, minor);
			
			
			ps.executeUpdate();
			cs.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			printSQLException(e);
			if (con != null) {
	            try {
	                con.rollback();
	            } catch(SQLException excep) {
	            	printSQLException(excep);
	            }
	        }
		} finally {
		    DbUtils.closeQuietly(ps);
		    DbUtils.closeQuietly(cs);
		    DbUtils.closeQuietly(con);
		}	
	}

	@Override
	public Version getVersion() {
		Connection con = getConnection();
		Statement s = null;
		ResultSet rs = null;
		Version version = null;
		// TODO Ensure only one version ever exists
		try {
			String getVersion = String.format("SELECT * FROM %s;", VERSIONS_TABLE_NAME);
			s = con.createStatement();
			rs = s.executeQuery(getVersion);
			rs.next(); // Move to the first row
			version = decodeVersion(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    DbUtils.closeQuietly(rs);
		    DbUtils.closeQuietly(s);
		    DbUtils.closeQuietly(con);
		}
		
		return version;
	}
	
	public static void printSQLException(SQLException ex) {
	    ex.printStackTrace(System.err);
	    System.err.println("SQLState: " + ex.getSQLState());
	    System.err.println("Error Code: " + ex.getErrorCode());
	    System.err.println("Message: " + ex.getMessage());
	    System.err.println("Cause: " + ex.getCause());
	}
}
