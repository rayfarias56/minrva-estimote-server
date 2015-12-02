package edu.illinois.ugl.minrva.resources;

<<<<<<< HEAD
import java.util.Collections;
=======
>>>>>>> Manually tested Api and fixed the issues
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
<<<<<<< HEAD
		return dao.getBeacons(versionId);
=======
		return dao.getBeaconsByVersion(versionId);
>>>>>>> Manually tested Api and fixed the issues
	}
	
	@Path("{major}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
<<<<<<< HEAD
	public List<Beacon> getBeacons(@PathParam("major") String major) {
		return dao.getBeacons(versionId, Integer.getInteger(major));
=======
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean newBeacon(Beacon beacon) {
		// TODO input should have optional x, y, z
		return dao.createBeacon(versionId, beacon.getUuid(), beacon.getMajor(), beacon.getMinor(), -1, -1, -1);
>>>>>>> Manually tested Api and fixed the issues
	}
	
	@Path("{major}/{minor}")
	public BeaconResource getBeacon(@PathParam("major") String major, @PathParam("minor") String minor) {
		return new BeaconResource(versionId, Integer.valueOf(major), Integer.valueOf(minor));
	}

}
