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

@Path("ebook")
public class EbookResource {

	@GET
	@Path("{bib_id}")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUrl(@PathParam("bib_id") String bibId) {
		String link = EbookUrlRetriever.getUrlByBibId(bibId);

		if (link == null) {
			return Response.status(Status.BAD_REQUEST)
					.entity(new JSONObject().put("error", "Couldn't find link.").toString())
					.build();
		}
		
		return Response.status(Status.ACCEPTED)
				.entity(new JSONObject().put("ebook_url", link).toString()).build();
	}

	private static class EbookUrlRetriever {
		private static final String BASE_URL = "http://vufind.carli.illinois.edu/vf-uiu/Record/";

		public static String getUrlByBibId(String bibId) {
			try {
				Document doc = Jsoup.connect(BASE_URL + bibId).get();

				Elements tags = doc.select("a:contains(ScienceDirect - Full text online)");			
				if (!tags.isEmpty()) {
					Element anchor = tags.first();
					
					String link = anchor.attr("href");
					if (!link.isEmpty()) {
						return link;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
	}
}
