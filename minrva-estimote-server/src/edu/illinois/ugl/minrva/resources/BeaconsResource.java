package edu.illinois.ugl.minrva.resources;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.data.BeaconDao;
import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.models.Beacon;
import edu.illinois.ugl.minrva.models.Version;

public class BeaconsResource {

	long versionId;
	
	BeaconDao dao = Database.INSTANCE;

	public BeaconsResource(long versionId) {
		this.versionId = versionId;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons() {
		return dao.getBeacons(versionId);
	}
	
	@Path("{major}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Beacon> getBeacons(@PathParam("major") String major) {
		return dao.getBeacons(versionId, Integer.getInteger(major));
	}
	
	@Path("{major}/{minor}")
	public BeaconResource getBeacon(@PathParam("major") String major, @PathParam("minor") String minor) {
		return new BeaconResource(versionId, Integer.valueOf(major), Integer.valueOf(minor));
	}

}
