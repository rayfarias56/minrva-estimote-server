package edu.illinois.ugl.minrva.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.illinois.ugl.minrva.data.DataException;
import edu.illinois.ugl.minrva.data.daos.BeaconDao;
import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.models.WayfinderError;

@Path("beacons")
public class BeaconsResource {

	@Inject
	private BeaconDao dao;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBeacon(Beacon beacon) {

		// If beacon has non-Java defined default values, they weren't provided by the user.
		if (beacon.getUuid() == null || beacon.getMajor() == -1 || beacon.getMinor() == -1) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new WayfinderError("Either the uuid, major, or minor weren't provided"))
					.build();
		}

		try {
			dao.createBeacon(beacon);
		} catch (DataException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		}
		return Response.status(Status.CREATED).build();
	}

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeacons() {
		try {
			return Response.status(Status.OK).entity(dao.getBeacons()).build();
		} catch (DataException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		}
	}

	@GET
	@PermitAll
	@Path("{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeacons(@PathParam("uuid") String uuid) {
		try {
			return Response.status(Status.OK).entity(dao.getBeacons(uuid)).build();
		} catch (DataException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		}
	}

	@GET
	@PermitAll
	@Path("{uuid}/{major}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeacons(@PathParam("uuid") String uuid, @PathParam("major") String major) {
		try {
			return Response.status(Status.OK).entity(dao.getBeacons(uuid, Integer.parseInt(major)))
					.build();
		} catch (DataException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		} catch (NumberFormatException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new WayfinderError("Major id wasn't a number")).build();
		}

	}

	@PermitAll
	@Path("{uuid}/{major}/{minor}")
	public BeaconResource getBeacon(@PathParam("uuid") String uuid,
			@PathParam("major") String major, @PathParam("minor") String minor) {
		return new BeaconResource(dao, uuid, major, minor);
	}
}
