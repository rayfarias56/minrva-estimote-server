package edu.illinois.ugl.minrva.resources;

import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import edu.illinois.ugl.minrva.authentication.JwtCodec;
import edu.illinois.ugl.minrva.authentication.PasswordStorage;
import edu.illinois.ugl.minrva.authentication.PasswordStorage.CannotPerformOperationException;
import edu.illinois.ugl.minrva.authentication.PasswordStorage.InvalidHashException;
import edu.illinois.ugl.minrva.data.DataException;
import edu.illinois.ugl.minrva.data.daos.UserDao;
import edu.illinois.ugl.minrva.models.User;
import edu.illinois.ugl.minrva.models.WayfinderError;

@Path("user")
public class UserResource {
	@Inject
	UserDao dao;

	@POST
	@Path("authenticate")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(User user_credentials) {
		try {
			Optional<User> stored_user = dao.getUser(user_credentials.getUsername());

			if (stored_user.isPresent() && PasswordStorage.verifyPassword(
					user_credentials.getPassword(), stored_user.get().getPassword())) {

				String jwt = JwtCodec.createJwt(stored_user.get().getUsername());
				return Response.status(Status.OK)
						.entity(new JSONObject().put("token", jwt).toString()).build();
			}
		} catch (CannotPerformOperationException | InvalidHashException | DataException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Internal database error")).build();
		}

		return Response.status(Status.BAD_REQUEST)
				.entity(new WayfinderError("Invalid username or password")).build();
	}

}