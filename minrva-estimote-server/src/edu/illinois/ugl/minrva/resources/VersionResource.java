package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.daos.VersionsDao;
import edu.illinois.ugl.minrva.daos.VersionsDaoMockImpl;
import edu.illinois.ugl.minrva.models.Version;


public class VersionResource {
	
	Long versionId;
	VersionsDao dao = new VersionsDaoMockImpl();

	public VersionResource(String id) {
		versionId = Long.parseLong(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Version getVersion() {
		return dao.findByVersionId(versionId);
	}

}
