package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("versions")
public class VersionsResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getVersions() {
		// TODO implement getVersions
		return "Will return a list of all versions in order of most recent, the production version will be flagged in another way";
	}
	
	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public String newVersion() {
		// TODO implement newVersion
		return "Will create a new version and returns the related ID and date, the production version will be flagged in another way";
	}
	
	@Path("production")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getProductionVersion(){
		// TODO implement getProductionVersion
		return "Returns the current production version id";
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
