package edu.illinois.ugl.minrva.resources;

import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.illinois.ugl.minrva.data.DataException;
import edu.illinois.ugl.minrva.data.daos.BeaconDao;
import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.models.WayfinderError;

public class BeaconResource {
	String uuid, major, minor;

	private BeaconDao dao;

	public BeaconResource(BeaconDao dao, String uuid, String major, String minor) {
		this.dao = dao;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
	}

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBeacon() {

		try {
			Optional<Beacon> beacon = dao.getBeacon(uuid, Integer.parseInt(major),
					Integer.parseInt(minor));

			if (beacon.isPresent()) {
				return Response.status(Status.OK).entity(beacon.get()).build();
			}
		} catch (NumberFormatException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new WayfinderError("Major/Minor id wasn't a number")).build();
		} catch (DataException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		}

		return Response.status(Status.OK).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBeacon(Beacon beacon) {
		try {
			beacon.setUuid(uuid);
			beacon.setMajor(Integer.parseInt(major));
			beacon.setMinor(Integer.parseInt(minor));
			if (dao.updateBeacon(beacon)) {
				return Response.status(Status.OK).build();
			};
		} catch (DataException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		}
		
		return Response.status(Status.BAD_REQUEST).entity(new WayfinderError("Beacon didn't exist"))
				.build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBeacon() {
		try {
			if (dao.deleteBeacon(uuid, Integer.parseInt(major), Integer.parseInt(minor))) {
				return Response.status(Status.OK).build();
			}
		} catch (NumberFormatException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new WayfinderError("Major/Minor id wasn't a number")).build();
		} catch (DataException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		}
		return Response.status(Status.BAD_REQUEST).entity(new WayfinderError("Beacon didn't exist"))
				.build();
	}

}
