package edu.illinois.ugl.minrva.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.VersionDao;
import edu.illinois.ugl.minrva.models.Version;


@Path("version")
public class VersionResource {
	@Inject
	VersionDao dao;
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Version getVersion() {
		return dao.getVersion();
	}
}
