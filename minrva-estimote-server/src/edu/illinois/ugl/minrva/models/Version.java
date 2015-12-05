package edu.illinois.ugl.minrva.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Version {
	
	private long id;

	public long getId() {
		return id;
	}

	public Version() {	} // Java Bean Requirement
	
	public Version(Long id) {	
		this.id = id;
	}
}
