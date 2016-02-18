package edu.illinois.ugl.minrva.data;

import java.util.List;
import edu.illinois.ugl.minrva.models.Beacon;

public interface BeaconDao {
	List<Beacon> getBeacons();
	List<Beacon> getBeacons(String uuid);
	List<Beacon> getBeacons(String uuid, int major);
	Beacon getBeacon(String uuid, int major, int minor);
	void createBeacon(Beacon beacon);
	void updateBeacon(Beacon beacon);
	void deleteBeacon(String uuid, int major, int minor);
}
