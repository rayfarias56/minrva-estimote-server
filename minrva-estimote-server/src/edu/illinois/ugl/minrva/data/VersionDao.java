package edu.illinois.ugl.minrva.data;

import java.util.List;

import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.models.NewVersion;
import edu.illinois.ugl.minrva.models.Version;

public interface VersionDao {
	List<Version> getVersions();
	Version getVersion(long id);
	Version getProductionVersion();
	long createVersion(NewVersion version);
}
