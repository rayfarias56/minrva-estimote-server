package edu.illinois.ugl.minrva.models;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Version {
	private long versionId;
	private long creationTime;
	private boolean isProduction;


	public long getVersionId() {
		return versionId;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isProduction() {
		return isProduction;
	}

	public void setProduction(boolean isProduction) {
		this.isProduction = isProduction;
	}

	public Version() {
	}

	public Version(long versionId, long creationTime, boolean isProduction) {
		this.versionId = versionId;
		this.creationTime = creationTime;
		this.isProduction = isProduction;
	}

	public void setProductionStatus(boolean isProduction) {
		this.isProduction = isProduction;
	}

	public static Comparator<Version> getComparator() {
		return new Comparator<Version>() {
			public int compare(Version v1, Version v2) {
				return (int) (v2.getCreationTime() - v2.getCreationTime());
			}
		};
	}
}
