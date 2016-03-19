package edu.illinois.ugl.minrva.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.illinois.ugl.minrva.data.DataException;
import edu.illinois.ugl.minrva.data.daos.VersionDao;
import edu.illinois.ugl.minrva.models.Version;
import edu.illinois.ugl.minrva.models.WayfinderError;

@Path("version")
public class VersionResource {
	@Inject
	VersionDao dao;

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVersion() {

		try {
			Version version = dao.getVersion();

			return Response.status(Status.OK).entity(version).build();
		} catch (DataException e) {

		}

		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(new WayfinderError("Internal database error")).build();

	}
}
