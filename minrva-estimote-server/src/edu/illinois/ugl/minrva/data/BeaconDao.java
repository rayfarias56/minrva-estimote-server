package edu.illinois.ugl.minrva.data;

import java.util.List;
import edu.illinois.ugl.minrva.models.Beacon;

public interface BeaconDao {
	List<Beacon> getBeacons();
	List<Beacon> getBeacons(int uuid);
	List<Beacon> getBeacons(int uuid, int major);
	Beacon getBeacon(int uuid, int major, int minor);
	void createBeacon(Beacon beacon);
	void updateBeacon(Beacon beacon);
	void deleteBeacon(int uuid, int major, int minor);
}
