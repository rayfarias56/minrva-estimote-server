package edu.illinois.ugl.minrva.data.daos;


import edu.illinois.ugl.minrva.data.DataException;
import edu.illinois.ugl.minrva.models.Version;


public interface VersionDao {
	Version getVersion() throws DataException;
}
