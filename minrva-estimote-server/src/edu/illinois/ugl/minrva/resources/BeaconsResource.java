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
	
// TODO decide if beacon level creation is even needed
//	@Consumes(MediaType.APPLICATION_JSON)
//	public boolean newBeacon(Beacon beacon) {
//		// TODO input should have optional x, y, z
//		return dao.createBeacon(versionId, beacon.getUuid(), beacon.getMajor(), beacon.getMinor(), -1, -1, -1);
//	}
	
	@Path("{major}/{minor}")
	public BeaconResource getBeacon(@PathParam("major") String major, @PathParam("minor") String minor) {
		return new BeaconResource(versionId, Integer.valueOf(major), Integer.valueOf(minor));
	}

}
