package edu.illinois.ugl.minrva.data.daos;

import java.util.List;
import java.util.Optional;

import edu.illinois.ugl.minrva.data.DataException;
import edu.illinois.ugl.minrva.models.Beacon;

public interface BeaconDao {
	List<Beacon> getBeacons() throws DataException;
	List<Beacon> getBeacons(String uuid) throws DataException;
	List<Beacon> getBeacons(String uuid, int major) throws DataException;
	Optional<Beacon> getBeacon(String uuid, int major, int minor) throws DataException;
	void createBeacon(Beacon beacon) throws DataException;
	Boolean updateBeacon(Beacon beacon) throws DataException;
	Boolean deleteBeacon(String uuid, int major, int minor) throws DataException;
}   
