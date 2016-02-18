package edu.illinois.ugl.minrva.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import edu.illinois.ugl.minrva.authentication.JwtCodec;
import edu.illinois.ugl.minrva.authentication.PasswordStorage;
import edu.illinois.ugl.minrva.authentication.PasswordStorage.CannotPerformOperationException;
import edu.illinois.ugl.minrva.authentication.PasswordStorage.InvalidHashException;
import edu.illinois.ugl.minrva.data.UserDao;
import edu.illinois.ugl.minrva.models.User;

@Path("user")
public class UserResource {
	@Inject
	UserDao dao;

	@POST
	@Path("authenticate")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	public String authenticateUser(User user_credentials) {
		User stored_user = dao.getUser(user_credentials.getUsername());

		try {
			if (stored_user != null && PasswordStorage
					.verifyPassword(user_credentials.getPassword(), stored_user.getPassword())) {

				return JwtCodec.createJwt(stored_user.getUsername());
			}
		} catch (CannotPerformOperationException | InvalidHashException e) {
			e.printStackTrace();
		}

		return ""; // TODO: should return 400 code
	}

}