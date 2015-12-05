package edu.illinois.ugl.minrva.resources;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.models.Beacon;

@Path("beacons")
public class BeaconsResource {

	BeaconDao dao = Database.INSTANCE;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons() {
		return dao.getBeacons();
	}
	
	@GET
	@Path("{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons(@PathParam("uuid") String uuid) {
		return dao.getBeacons(Integer.parseInt(uuid));
	}
	
	@GET
	@Path("{uuid}/{major}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons(@PathParam("uuid") String uuid, @PathParam("major") String major) {
		return dao.getBeacons(Integer.parseInt(uuid), Integer.parseInt(major));
	}
	
	@Path("{uuid}/{major}/{minor}")
	public BeaconResource getBeacon(@PathParam("uuid") String uuid, @PathParam("major") String major, @PathParam("minor") String minor) {
		return new BeaconResource(Integer.parseInt(uuid), Integer.parseInt(major), Integer.parseInt(minor));
	}
}
