package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.daos.BeaconsDao;
import edu.illinois.ugl.minrva.daos.BeaconsDaoMockImpl;
import edu.illinois.ugl.minrva.models.Beacon;

public class BeaconResource {
	
	long versionId;
	int uuid;
	int major;
	int minor;
	
	BeaconsDao dao = new BeaconsDaoMockImpl();

	public BeaconResource(long versionId, String beaconId) {
		this.versionId = versionId;
		
		String[] ids = beaconId.split("-");
		this.uuid = Integer.parseInt(ids[0]);
		this.uuid = Integer.parseInt(ids[1]);
		this.uuid = Integer.parseInt(ids[2]);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Beacon getBeacon() {
		return dao.getBeaconById(versionId, uuid, major, minor);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteBeacon() {
		return dao.deleteBeacon(versionId, uuid, major, minor);
	}
	
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean updateBeacon(Beacon beacon) {
		return dao.updateBeacon(versionId, uuid, major, minor, beacon.getX(), beacon.getY(), beacon.getZ());
	}
}
