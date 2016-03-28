package edu.illinois.ugl.minrva.models;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class WayfinderError {

	private String message;
	
	public WayfinderError() { }

	public WayfinderError(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
