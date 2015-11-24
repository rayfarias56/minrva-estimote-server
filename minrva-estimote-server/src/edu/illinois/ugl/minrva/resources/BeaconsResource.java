package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


public class BeaconsResource {

	String versionId;
	
	public BeaconsResource(String id) {
		versionId = id;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getBeacons() {
		// TODO implement getBeacons
		return "Return all beacons associated with " + versionId;
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String newBeacon() {
		// TODO implement newBeacon
		return "Creates a new beacon within the " + versionId + " given uuid, major, and minor. Returns nothing.";
	}
	
	@Path("{beacon}")
	public BeaconResource getBeacon(@PathParam("beacon") String beaconId) {
		// TODO implement getBeacon
		return new BeaconResource(versionId, beaconId);
	}
	
}
