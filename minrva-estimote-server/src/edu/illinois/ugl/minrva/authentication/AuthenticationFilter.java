package edu.illinois.ugl.minrva.authentication;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

	@Context
	protected ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Bearer";

	/*
	 * TODO Bad authentication responses should have their own class along with
	 * perhaps a 404 response. These should be put in another class.
	 */

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		Method method = resourceInfo.getResourceMethod();

		if (method.isAnnotationPresent(PermitAll.class)) {
			return;
		}

		if (method.isAnnotationPresent(DenyAll.class)) {
			Response accessForbidden = Response.status(Response.Status.FORBIDDEN)
					.entity("Access blocked.").build();
			requestContext.abortWith(accessForbidden);
			return;
		}

		// Fetch authorization header
		final List<String> authorization = requestContext.getHeaders().get(AUTHORIZATION_PROPERTY);

		if (authorization == null || authorization.isEmpty()) {
			Response accessDenied = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Access blocked.").build();
			requestContext.abortWith(accessDenied);
			return;
		}

		// Use authorization header to verify authenticity
		String jwt = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		if (!JwtCodec.isValidJwt(jwt)) {
			Response accessDenied = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Insert Sign-in page here, perhaps with error message.").build();
			requestContext.abortWith(accessDenied);
		}		
	}
}
