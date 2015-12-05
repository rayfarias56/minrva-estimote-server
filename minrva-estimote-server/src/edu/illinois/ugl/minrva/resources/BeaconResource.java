package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.models.Beacon;
	
public class BeaconResource {
	// TODO URL/Paylod conflicts
	int uuid, major, minor;
	
	BeaconDao dao = Database.INSTANCE;

	public BeaconResource(int uuid, int major, int minor) {
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Beacon getBeacon() {
		return dao.getBeacon(uuid, major, minor);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createBeacon(Beacon beacon) {
		// TODO handle failure
		dao.createBeacon(beacon);
		return true;
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
