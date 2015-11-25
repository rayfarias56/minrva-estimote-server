package edu.illinois.ugl.minrva.daos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.databases.MockData;

public class VersionsDaoMockImpl implements VersionsDao {

	@Override
	public List<Version> listAll() {
		List<Version> list = new ArrayList<>(MockData.instance.getVersionsContent().values());
		Collections.sort(list, Version.getComparator());
		return list;
	}

	@Override
	public Version getProductionVersion() {
		return MockData.instance.getVersionsContent().get(MockData.instance.getProductionVersion());
	}

	@Override
	public Version findByVersionId(Long versionId) { 
		return MockData.instance.getVersionsContent().get(versionId);
	}

	@Override
	public long createVersion() {
		long versionId = MockData.instance.generateVersionId();

		MockData.instance.getVersionsContent().put(versionId,
				new Version(versionId, System.currentTimeMillis(), false));
		return versionId;
	}
}
