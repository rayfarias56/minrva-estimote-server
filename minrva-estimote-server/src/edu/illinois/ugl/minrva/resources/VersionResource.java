package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.data.VersionDao;
import edu.illinois.ugl.minrva.models.Version;


public class VersionResource {
	
	long id;
	
	VersionDao dao = Database.INSTANCE;

	public VersionResource(long id) {
		this.id = id;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Version getVersion() {
		return dao.getVersion(id);
	}
	
	@Path("beacons")
	public BeaconsResource getBeacons() {
		return new BeaconsResource(id);
	}
	
}
