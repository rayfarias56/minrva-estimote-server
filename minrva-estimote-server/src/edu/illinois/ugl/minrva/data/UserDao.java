package edu.illinois.ugl.minrva.data;

import edu.illinois.ugl.minrva.models.User;

public interface UserDao {
	User getUser(String username);
}
