package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.models.Beacon;

public class BeaconResource {
	
	long versionId;
	int major;
	int minor;
	
	BeaconDao dao = Database.INSTANCE;

	public BeaconResource(long versionId, int major, int minor) {
		this.versionId = versionId;
		this.major = major;
		this.minor = minor;
		
		// TODO decide the method of passing beacon "ids"
//		String[] ids = beaconId.split("-");
//		this.uuid = Integer.parseInt(ids[0]);
//		this.major = Integer.parseInt(ids[1]);
//		this.minor = Integer.parseInt(ids[2]);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Beacon getBeacon() {
		return dao.getBeacon(versionId, major, minor);
	}

	// TODO decide if delete is needed
//	@DELETE
//	@Produces(MediaType.APPLICATION_JSON)
//	public boolean deleteBeacon() {
//		return dao.deleteBeacon(versionId, major, minor);
//	}
	
	// TODO decide if update beacon is needed
//	@PUT
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public boolean updateBeacon(Beacon beacon) {
//		return dao.updateBeacon(versionId, major, minor, beacon.getX(), beacon.getY(), beacon.getZ());
//	}
}
