package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class BeaconResource {
	
	String versionId;
	String beaconId;

	public BeaconResource(String versionId, String beaconId) {
		this.versionId = versionId;
		this.beaconId = beaconId;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getBeacon() {
		// TODO implement getBeacon
		return "returns beacon data, UUID, major, minor, and location (x,y,z)";
	}

	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBeacon() {
		// TODO implement deleteBeacon
		return "returns date of the version";
	}
	
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBeacon() {
		// TODO implement updateBeacon
		return "updates a beacons x, y, z";
	}
}
