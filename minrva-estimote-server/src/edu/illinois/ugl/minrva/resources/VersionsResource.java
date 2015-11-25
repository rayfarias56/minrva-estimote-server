package edu.illinois.ugl.minrva.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.daos.VersionsDao;
import edu.illinois.ugl.minrva.daos.VersionsDaoMockImpl;
import edu.illinois.ugl.minrva.models.Version;

@Path("versions")
public class VersionsResource {
	
	VersionsDao dao = new VersionsDaoMockImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Version> getVersions() {
		return dao.listAll();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public long newVersion(Version input) {
		// TODO input should have optional x, y, z
		return dao.createVersion();
	}
	
	@Path("production")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Version getProductionVersion(){
		return dao.getProductionVersion();
	}
	
	@Path("{version}")
	public VersionResource getTodo(@PathParam("version") String id) {
		return new VersionResource(id);
	}
	
	@Path("{version}/beacons")
	public BeaconsResource getBeacons(@PathParam("version") String id) {
		return new BeaconsResource(id);
	}
	

}
