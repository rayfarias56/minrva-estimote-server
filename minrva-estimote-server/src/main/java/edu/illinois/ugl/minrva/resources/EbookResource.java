package edu.illinois.ugl.minrva.resources;

import java.io.IOException;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.illinois.ugl.minrva.models.WayfinderError;

@Path("ebook")
public class EbookResource {

	@GET
	@Path("{bib_id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUrl(@PathParam("bib_id") String bibId) {
		String link = null;
		try {
			link = EbookUrlRetriever.getUrlByBibId(bibId);
		} catch (VufindIOException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(new WayfinderError("Couldn't connect to vufind website.")).build();
		} catch (BadBibIdException e) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new WayfinderError("No vufind page for requested BibId.")).build();
		}

		if (link == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new WayfinderError("Couldn't find ebook link.")).build();
		}

		return Response.status(Status.OK).entity(new JSONObject().put("ebook_url", link).toString())
				.build();
	}

	private static class EbookUrlRetriever {

		private static final String BASE_URL = "http://vufind.carli.illinois.edu/vf-uiu/Record/";

		public static String getUrlByBibId(String bibId)
				throws VufindIOException, BadBibIdException {

			String vufindUrl = BASE_URL + bibId;
			Document doc = null;
			try {
				doc = Jsoup.connect(vufindUrl).get();
			} catch (IOException e) {
				throw new VufindIOException(String.format(
						"The url: %s generated from the bibId: %s could not be connected to.",
						vufindUrl, bibId));
			}

			Elements onlineAccessField = doc.select("th:contains(Online Access:) + td").select("a");
			if (!onlineAccessField.isEmpty()) {
				Element anchor = onlineAccessField.first();
				String link = anchor.attr("href");
				if (!link.isEmpty()) {
					return link;
				}
			} else {
				if (!doc.select("title:contains(Library Resource Finder: Oops)").isEmpty()) {
					throw new BadBibIdException(String.format("No book page for bibId: %s", bibId));
				}
			}

			return null;
		}
	}

	@SuppressWarnings("serial")
	private static class VufindIOException extends Exception {
		public VufindIOException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	private static class BadBibIdException extends Exception {
		public BadBibIdException(String message) {
			super(message);
		}
	}
}