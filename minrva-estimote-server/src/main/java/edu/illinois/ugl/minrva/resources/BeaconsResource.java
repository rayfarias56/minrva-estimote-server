package edu.illinois.ugl.minrva.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.models.Beacon;

@Path("beacons")
public class BeaconsResource {

	@Inject
	private BeaconDao dao;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createBeacon(Beacon beacon) {
		// TODO handle failure
		dao.createBeacon(beacon);
		return true;
	}

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons() {
		return dao.getBeacons();
	}

	@GET
	@Path("{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons(@PathParam("uuid") String uuid) {
		return dao.getBeacons(uuid);
	}

	@GET
	@PermitAll
	@Path("{uuid}/{major}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons(@PathParam("uuid") String uuid,
			@PathParam("major") String major) {
		return dao.getBeacons(uuid, Integer.parseInt(major));
	}

	@PermitAll
	@Path("{uuid}/{major}/{minor}")
	public BeaconResource getBeacon(@PathParam("uuid") String uuid,
			@PathParam("major") String major, @PathParam("minor") String minor) {
		return new BeaconResource(dao, uuid, Integer.parseInt(major), Integer.parseInt(minor));
	}
}
