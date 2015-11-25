package edu.illinois.ugl.minrva.daos;

import java.util.List;
import edu.illinois.ugl.minrva.models.Version;

public interface VersionsDao {
	List<Version> listAll();
	Version getProductionVersion();
	long createVersion();
	Version findByVersionId(Long versionId);
}
