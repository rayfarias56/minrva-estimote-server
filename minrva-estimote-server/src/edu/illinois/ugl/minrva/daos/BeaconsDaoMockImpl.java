package edu.illinois.ugl.minrva.daos;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.databases.MockData;

public class BeaconsDaoMockImpl implements BeaconsDao {

	@Override
	public List<Beacon> getBeaconsByVersion(long id) {
		List<Beacon> matches = new ArrayList<>();
		List<Beacon> beacons = new ArrayList<>(MockData.instance.getBeaconsContent().values());
		for (Beacon b : beacons) {
			if (b.getVersionId() == id) {
				matches.add(b);
			}
		}

		return matches;
	}

	@Override
	public boolean createBeacon(long vId, int uuid, int major, int minor, int x, int y, int z) {
		String bId = MockData.instance.getBeaconId(vId, uuid, major, minor);
		Beacon beacon = new Beacon(vId, uuid, major, minor, x, y, z);

		MockData.instance.getBeaconsContent().put(bId, beacon);
		return true;
	}

	@Override
	public boolean updateBeacon(long vId, int uuid, int major, int minor, int x, int y, int z) {
		String bId = MockData.instance.getBeaconId(vId, uuid, major, minor);
		Beacon b = MockData.instance.getBeaconsContent().get(bId);
		b.setLocation(x, y, z);
		return true;
	}

	@Override
	public boolean deleteBeacon(long vId, int uuid, int major, int minor) {
		String bId = MockData.instance.getBeaconId(vId, uuid, major, minor);
		MockData.instance.getBeaconsContent().remove(bId);
		return true;
	}

	@Override
	public Beacon getBeaconById(long vId, int uuid, int major, int minor) {
		String bId = MockData.instance.getBeaconId(vId, uuid, major, minor);
		return MockData.instance.getBeaconsContent().get(bId);
	}

}
