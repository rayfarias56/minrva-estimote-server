package edu.illinois.ugl.minrva.models;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Beacon {
	private String uuid = null;
	private int major, minor = -1;
	private double x, y, z;
	private String description;

	public String getUuid() {
		return uuid;
	}
	
	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Beacon() { } // Java Bean Requirement

	public Beacon(String uuid, int major, int minor, double x, double y, double z, String description) {
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.x = x;
		this.y = y;
		this.z = z;
		this.description = description;
	}
	
	public class BeaconComparator implements Comparator<Beacon> {
		public int compare(Beacon b1, Beacon b2) {
			int comp = b1.major - b2.major;
			if (comp != 0)
				return comp;
			
			return b1.minor - b2.minor;
		}
	}

}
