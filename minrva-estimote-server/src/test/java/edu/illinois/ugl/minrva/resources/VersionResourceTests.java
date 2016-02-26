package edu.illinois.ugl.minrva.resources;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;


import edu.illinois.ugl.minrva.application.WayfinderApplication;
import edu.illinois.ugl.minrva.data.Database;
import edu.illinois.ugl.minrva.models.Version;

/*
 * This class was created mostly to give an example and test the moving parts necessary to test
 * the resources such as mockito and the JerseyTest Framework.
 */
@RunWith(MockitoJUnitRunner.class)
public class VersionResourceTests extends JerseyTest {
	
	private Database dao;
	
	
	// Start the server
	@Override
	protected Application configure() {
		dao = Mockito.mock(Database.class);
		return new WayfinderApplication(dao);
	}
	
	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		Mockito.reset(dao);
	}
	
	@Test
	public void testGetVersionReturnsNumber() {
		Version expected = new Version(5L);
		when(dao.getVersion()).thenReturn(expected);
		
		final String resourcePath = "version/";
		final Response response = target(resourcePath).request(MediaType.APPLICATION_JSON_TYPE).get();
		
		Version actual = response.readEntity(Version.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		assertEquals(expected.getId(), actual.getId());
		response.close();
	}

}

