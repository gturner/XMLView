package au.edu.anu.xmlview.fedora;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class GSearchHelper {
	final Logger log = LoggerFactory.getLogger(GSearchHelper.class);
	
	public GSearchHelper(){
		
	}
	
	public void getItems(){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter("fedoraAdmin", "fedoraAdmin"));
		WebResource service = client.resource(getBaseURI());
		// http://localhost:8380/fedoragsearch/rest?operation=gfindObjects&query=item.kind%3A%22activity%22&hitPageSize=
		ClientResponse response = service.path("fedoragsearch").path("rest").queryParam("restXslt", "copyXml").queryParam("operation", "gfindObjects").queryParam("query", "item.kind:'activity'").accept(MediaType.TEXT_XML).get(ClientResponse.class);
		log.info(response.getEntity(String.class));
	}
	
	private static URI getBaseURI(){
		return UriBuilder.fromUri("http://localhost:8380").build();
	}
}
