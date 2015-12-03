package edu.illinois.ugl.minrva.models;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Beacon {

	private int major, minor;
	private float x, y, z;

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public Beacon(int major, int minor, float x, float y, float z) {
		this.major = major;
		this.minor = minor;
		this.x = x;
		this.y = y;
		this.z = z;
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
