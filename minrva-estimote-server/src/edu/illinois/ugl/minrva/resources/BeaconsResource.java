package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.daos.BeaconsDao;
import edu.illinois.ugl.minrva.daos.BeaconsDaoMockImpl;
import edu.illinois.ugl.minrva.models.Beacon;

public class BeaconsResource {

	long versionId;
	BeaconsDao dao = new BeaconsDaoMockImpl();

	public BeaconsResource(String id) {
		versionId = Long.parseLong(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Beacon getBeacons(Beacon beacon) {
		return dao.getBeaconById(versionId, beacon.getUuid(), beacon.getMajor(), beacon.getMinor());
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean newBeacon(Beacon beacon) {
		return dao.createBeacon(versionId, beacon.getUuid(), beacon.getMajor(), beacon.getMinor(), -1, -1, -1);
	}

	@Path("{beacon}")
	public BeaconResource getBeacon(@PathParam("beacon") String beaconId) {
		return new BeaconResource(versionId, beaconId);
	}

}
