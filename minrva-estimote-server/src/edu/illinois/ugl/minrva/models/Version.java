package edu.illinois.ugl.minrva.models;

import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Version extends NewVersion {
	
	private long id;

	public long getId() {
		return id;
	}

	public Version(long id, boolean isProduction, List<Beacon> beacons) {
		super(isProduction, beacons);
		this.id = id;
	}
	
	public class VersionComparator implements Comparator<Version> {
		public int compare(Version v1, Version v2) {
			long comp = v1.getId() - v2.getId();
			if (comp > 0)
				return 1;
			else if (comp < 0)
				return -1;
			else
				return 0;
		}
	}

}
