package edu.illinois.ugl.minrva.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Beacon {
	private long versionId;
	private int uuid, major, minor;
	private int x, y, z;
	

	public long getVersionId() {
		return versionId;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Beacon() {
	}

	public Beacon(long versionId, int uuid, int major, int minor, int x, int y,
			int z) {
		this.versionId = versionId;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
