package edu.illinois.ugl.minrva.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

import edu.illinois.ugl.minrva.data.DbConfig;
import edu.illinois.ugl.minrva.data.daos.BeaconDao;
import edu.illinois.ugl.minrva.data.daos.UserDao;
import edu.illinois.ugl.minrva.data.daos.VersionDao;
import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.models.User;

import org.apache.commons.dbutils.DbUtils;

public class Database
		implements VersionDao, BeaconDao, UserDao {

	public Database() { }

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(DbConfig.getUrl(), DbConfig.getUsername(),
				DbConfig.getPassword());
	}

	@Override
	public List<Beacon> getBeacons() throws DataException {
		Connection con = null;
		Statement s = null;
		ResultSet rs = null;
		List<Beacon> list = new ArrayList<Beacon>();

		try {
			con = getConnection();
			String getBeacons = String.format("SELECT * FROM %s;", Constants.BEACONS_TABLE_NAME);
			s = con.createStatement();
			rs = s.executeQuery(getBeacons);
			while (rs.next()) {
				list.add(SqlDecoders.decodeBeacon(rs));
			}
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
			throw new DataException(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(s);
			DbUtils.closeQuietly(con);
		}

		return list;
	}

	@Override
	public List<Beacon> getBeacons(String uuid) throws DataException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			con = getConnection();
			ps = con.prepareStatement(String.format("SELECT * FROM %s WHERE %s=?;",
					Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_UUID));
			ps.setString(1, uuid);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(SqlDecoders.decodeBeacon(rs));
			}
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
			throw new DataException(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}

		return list;
	}

	@Override
	public List<Beacon> getBeacons(String uuid, int major) throws DataException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Beacon> list = new ArrayList<Beacon>();
		try {
			con = getConnection();
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
			SqlExceptionPrinter.printSQLException(e);
			throw new DataException(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}

		return list;
	}

	@Override
	public Optional<Beacon> getBeacon(String uuid, int major, int minor) throws DataException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Beacon beacon = null;

		try {
			con = getConnection();
			ps = con.prepareStatement(
					String.format("SELECT * FROM %s WHERE %s=? AND %s=? AND %s=?;",
							Constants.BEACONS_TABLE_NAME, Constants.BEACONS_COL_UUID,
							Constants.BEACONS_COL_MAJOR, Constants.BEACONS_COL_MINOR));
			ps.setString(1, uuid);
			ps.setInt(2, major);
			ps.setInt(3, minor);
			rs = ps.executeQuery();

			rs.next();
			beacon = SqlDecoders.decodeBeacon(rs);
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
			throw new DataException(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}

		return Optional.ofNullable(beacon);
	}

	@Override
	public void createBeacon(Beacon beacon) throws DataException {
		Connection con = null;
		PreparedStatement ps = null;
		CallableStatement cs = null;
		try {
			con = getConnection();
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
			SqlExceptionPrinter.printSQLException(e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					e.printStackTrace();
				}
			}
			throw new DataException(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(cs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}
	}

	@Override
	public Boolean updateBeacon(Beacon beacon) throws DataException {
		Connection con = null;
		PreparedStatement ps = null;
		CallableStatement cs = null;
		try {
			con = getConnection();
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
			SqlExceptionPrinter.printSQLException(e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					e.printStackTrace();
				}
			}
			
			if (e.getSQLState() == "01001") {
				return false;
			}
			
			throw new DataException(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(cs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}
		
		return true;
	}

	@Override
	public Boolean deleteBeacon(String uuid, int major, int minor) throws DataException {
		Connection con = null;
		PreparedStatement ps = null;
		CallableStatement cs = null;
		try {
			con = getConnection();
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
			
			if (e.getSQLState() == "01001") {
				return false;
			}
			
			throw new DataException(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(cs);
			DbUtils.closeQuietly(con);
		}
		
		return true;
	}

	@Override
	public Version getVersion() throws DataException {
		Connection con = null;
		Statement s = null;
		ResultSet rs = null;
		Version version = null;

		try {
			String getVersion = String.format("SELECT * FROM %s;", Constants.VERSIONS_TABLE_NAME);

			con = getConnection();
			s = con.createStatement();
			rs = s.executeQuery(getVersion);

			rs.next();
			version = SqlDecoders.decodeVersion(rs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new DataException(e.getMessage());
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(s);
			DbUtils.closeQuietly(con);
		}

		return version;
	}

	@Override
	public Optional<User> getUser(String username) throws DataException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		try {
			con = getConnection();
			ps = con.prepareStatement(String.format("SELECT * FROM %s where %s = ?;",
					Constants.USERS_TABLE_NAME, Constants.USERS_COL_USERNAME));
			ps.setString(1, username);

			rs = ps.executeQuery();
			rs.next();
			user = SqlDecoders.decodeUser(rs);
		} catch (ClassNotFoundException e) {
			throw new DataException(e.getMessage());
		} catch (SQLException e) {
			SqlExceptionPrinter.printSQLException(e);
			throw new DataException(e.getMessage());
		} finally {
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(ps);
			DbUtils.closeQuietly(con);
		}

		return Optional.ofNullable(user);
	}
}
