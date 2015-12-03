package edu.illinois.ugl.minrva.data;

import java.util.List;
import edu.illinois.ugl.minrva.models.Beacon;

public interface BeaconDao {
	List<Beacon> getBeacons(long id);
	List<Beacon> getBeacons(long id, int major);
	Beacon getBeacon(long id, int major, int minor);
	void createBeacon(long versionId, Beacon beacon);
}
