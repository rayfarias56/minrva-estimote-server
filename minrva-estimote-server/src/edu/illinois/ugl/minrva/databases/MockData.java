package edu.illinois.ugl.minrva.databases;

import java.util.HashMap;
import java.util.Map;

import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.models.Beacon;

public enum MockData {
	instance;

	// Version related data.
	private Map<Long, Version> versionsContent = new HashMap<>();
	private long productionVersion;
	private long latestVersion;

	// Beacon related data.
	private Map<String, Beacon> beaconsContent = new HashMap<>();

	private MockData() {
		productionVersion = 56;
		latestVersion = 120;

		versionsContent.put((long) 3,
				new Version((long) 3, System.currentTimeMillis(), false));
		versionsContent.put(productionVersion,
				new Version((long) productionVersion, System.currentTimeMillis(), true));
		versionsContent.put((long) 117,
				new Version((long) 117, System.currentTimeMillis(), false));

		int UUID = 123467890;
		int major = 56;
		beaconsContent.put(getBeaconId(56, UUID, major, 11), new Beacon(
				(long) 56, UUID, major, /* versionId */11, /* x */1, /* y */2, /* z */
				3));
		beaconsContent.put(getBeaconId(56, UUID, major, 16), new Beacon(
				(long) 56, UUID, major, /* versionId */16,
				/* x */11, /* y */22, /* z */33));
		beaconsContent.put(getBeaconId(56, UUID, major, 12), new Beacon(
				(long) 56, UUID, major, /* versionId */22,
				/* x */111, /* y */222, /* z */333));

		beaconsContent.put(getBeaconId(117, UUID, major, 16), new Beacon(
				(long) 117, UUID, major, /* versionId */16,
				/* x */10, /* y */20, /* z */30));
	}

	public Map<Long, Version> getVersionsContent() {
		return versionsContent;
	}

	public long getProductionVersion() {
		return productionVersion;
	}
	
	public void setProductionVersion(long id) {
		productionVersion = id;
	}

	public long getLatestVersion() {
		return latestVersion;
	}

	public long generateVersionId() {
		latestVersion += 1;
		return latestVersion;
	}

	public Map<String, Beacon> getBeaconsContent() {
		return beaconsContent;
	}

	public String getBeaconId(long vId, int uuid, int major, int minor) {
		return vId + ":" + uuid + "," + major + "," + minor;
	}
}