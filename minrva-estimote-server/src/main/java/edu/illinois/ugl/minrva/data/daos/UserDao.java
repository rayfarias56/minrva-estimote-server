package edu.illinois.ugl.minrva.data.daos;

import java.util.Optional;

import edu.illinois.ugl.minrva.data.DataException;
import edu.illinois.ugl.minrva.models.User;

public interface UserDao {
	Optional<User> getUser(String username) throws DataException;
}

