package edu.illinois.ugl.minrva.models;

import java.util.List;

public class NewVersion {
	
	private boolean isProduction;
	private List<Beacon> beacons;

	public boolean isProduction() {
		return isProduction;
	}
	
	public List<Beacon> getBeacons() {
		return beacons;
	}

	public NewVersion(boolean isProduction, List<Beacon> beacons) {
		this.isProduction = isProduction;
		this.beacons = beacons;
	}

}
