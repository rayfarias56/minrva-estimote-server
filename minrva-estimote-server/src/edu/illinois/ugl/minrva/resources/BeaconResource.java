package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.models.Beacon;
	
public class BeaconResource {
	String uuid;
	int major, minor;
	
	private BeaconDao dao;
	
	public BeaconResource(BeaconDao dao, String uuid, int major, int minor) {
		this.dao = dao;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Beacon getBeacon() {
		return dao.getBeacon(uuid, major, minor);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateBeacon(Beacon beacon) {
		// TODO handle failure
		dao.updateBeacon(beacon);
		return true;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteBeacon() {
		// TODO handle failure
		dao.deleteBeacon(uuid, major, minor);
		return true;
	}
	
}
