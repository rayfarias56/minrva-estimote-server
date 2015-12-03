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
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Beacon getBeacon() {
		return dao.getBeacon(versionId, major, minor);
	}
	
}
