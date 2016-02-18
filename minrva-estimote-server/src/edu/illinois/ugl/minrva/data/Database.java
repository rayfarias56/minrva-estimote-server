package edu.illinois.ugl.minrva.data;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import edu.illinois.ugl.minrva.data.DbConfig;
import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.models.User;

import org.apache.commons.dbutils.DbUtils;

public enum Database implements VersionDao,BeaconDao,UserDao {
	INSTANCE;

	// TODO There's probably a way to make this class more 'D.R.Y.'
	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(DbConfig.getUrl(), DbConfig.getUsername(),
					DbConfig.getPassword());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	@Override
	public List<Beacon> getBeacons() {
		Connection con = getConnection();
		Statement s = null;
		ResultSet rs = null;
		List<Beacon> list = new ArrayList<Beacon>();

		try {
			String getBeacons = String.format("SELECT * FROM %s;", Constants.BEACONS_TABLE_NAME);
			s = con.createStatement();
			rs = s.executeQuery(getBeacons);
			while (rs.next()) {
				list.add(SqlDecoders.decodeBeacon(rs));
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
			ps = con.prepareStatement(String.format("SELECT * FROM %s WHERE %s=?;",
					Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_UUID));
			ps.setString(1, uuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(SqlDecoders.decodeBeacon(rs));
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
					Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_UUID,
					Constants.BEACONS_COL_MAJOR));
			ps.setString(1, uuid);
			ps.setInt(2, major);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(SqlDecoders.decodeBeacon(rs));
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
			ps = con.prepareStatement(
					String.format("SELECT * FROM %s WHERE %s=? AND %s=? AND %s=?;",
							Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_UUID,
							Constants.BEACONS_COL_MAJOR, Constants.BEACONS_COL_MINOR));
			ps.setString(1, uuid);
			ps.setInt(2, major);
			ps.setInt(3, minor);
			rs = ps.executeQuery();
			rs.next(); // Move to the first row
			beacon = SqlDecoders.decodeBeacon(rs);
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

			cs = con.prepareCall(Constants.INCREMENT_STORED_PROC_SQL);
			ps = con.prepareStatement(
					String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?);",
							Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_UUID,
							Constants.BEACONS_COL_MAJOR, Constants.BEACONS_COL_MINOR,
							Constants.BEACONS_COL_X, Constants.BEACONS_COL_Y,
							Constants.BEACONS_COL_Z, Constants.BEACONS_COL_DESCRIPTION));
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
				} catch (SQLException excep) {
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

			cs = con.prepareCall(Constants.INCREMENT_STORED_PROC_SQL);
			ps = con.prepareStatement(String.format(
					"UPDATE %s SET %s=?,  %s=?,  %s=?, %s=? " + "WHERE  %s=? AND %s=? AND %s=?;",
					Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_X, Constants.BEACONS_COL_Y,
					Constants.BEACONS_COL_Z, Constants.BEACONS_COL_DESCRIPTION,
					Constants.BEACONS_COL_UUID, Constants.BEACONS_COL_MAJOR,
					Constants.BEACONS_COL_MINOR));
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
				} catch (SQLException excep) {
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

			cs = con.prepareCall(Constants.INCREMENT_STORED_PROC_SQL);
			ps = con.prepareStatement(String.format("DELETE FROM %s WHERE  %s=? AND %s=? AND %s=?;",
					Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_UUID,
					Constants.BEACONS_COL_MAJOR, Constants.BEACONS_COL_MINOR));
			ps.setString(1, uuid);
			ps.setInt(2, major);
			ps.setInt(3, minor);

			ps.executeUpdate();
			cs.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					SqlExceptionPrinter.printSQLException(excep);
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
			String getVersion = String.format("SELECT * FROM %s;", Constants.VERSIONS_TABLE_NAME);
			s = con.createStatement();
			rs = s.executeQuery(getVersion);
			rs.next(); // Move to the first row
			version = SqlDecoders.decodeVersion(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(s);
			DbUtils.closeQuietly(con);
		}

		return version;
	}

	@Override
	public User getUser(String username) {
		Connection con = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		try {
			ps = con.prepareStatement(String.format("SELECT * FROM %s where %s = ?;",
					Constants.USERS_TABLE_NAME, Constants.USERS_COL_USERNAME));
			ps.setString(1, username);

			rs = ps.executeQuery();
			rs.next(); // Move to the first row
			user = SqlDecoders.decodeUser(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}

		return user;
	}
}
