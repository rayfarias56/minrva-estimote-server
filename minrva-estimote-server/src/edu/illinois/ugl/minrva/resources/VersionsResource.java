package edu.illinois.ugl.minrva.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.data.VersionDao;
import edu.illinois.ugl.minrva.models.NewVersion;
import edu.illinois.ugl.minrva.models.Version;

@Path("versions")
public class VersionsResource {
	
	VersionDao dao = Database.INSTANCE;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Version> getVersions() {
		return dao.getVersions();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public long createVersion(NewVersion version) {
		return dao.createVersion(version);
	}
	
	@Path("production")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Version getProductionVersion(){
		return dao.getProductionVersion();
	}
	
	@Path("production/{version}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public boolean setProductionVersion(@PathParam("version") String id){
		return dao.setProductionVersion(Long.parseLong(id));
	}
	
	@Path("{version}")
	public VersionResource getVersion(@PathParam("version") String id) {
		return new VersionResource(Long.valueOf(id));
	}

}
