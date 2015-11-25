package edu.illinois.ugl.minrva.daos;

import java.util.List;
import edu.illinois.ugl.minrva.models.Beacon;

public interface BeaconsDao {
	List<Beacon> getBeaconsByVersion(long id);
	boolean createBeacon(long vId, int uuid, int major, int minor, int x,
			int y, int z);
	boolean updateBeacon(long vId, int uuid, int major, int minor, int x,
			int y, int z);
	boolean deleteBeacon(long vId, int uuid, int major, int minor);
	Beacon getBeaconById(long vId, int uuid, int major, int minor);
}
