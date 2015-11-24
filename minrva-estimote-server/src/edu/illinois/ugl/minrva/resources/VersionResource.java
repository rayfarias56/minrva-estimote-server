package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


public class VersionResource {
	
	String versionId;

	public VersionResource(String id) {
		versionId = id;
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getVersion() {
		// TODO implement getVersion
		return "returns date of the version";
	}

}
