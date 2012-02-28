package au.edu.anu.xmlview.services;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.xmlview.fedora.FedoraHelper;

@Path("gsearch")
public class DisplayGSearchService {

	final Logger log = LoggerFactory.getLogger(DisplayGSearchService.class);
	private static final String ctx = "http://localhost:9380/XMLView/resources/";
	
	@Context UriInfo uriInfo;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public String getRifCs(@PathParam("id") String objId){
		log.info("Id: " + objId);
		StringWriter sw = new StringWriter();
		FedoraHelper fo = new FedoraHelper();
		
		InputStream xmlFormStream = fo.getObjectXML(objId);
		String xslFormString = ctx + "gsearch.xsl";
		try{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xmlSource = new StreamSource(xmlFormStream);
			//Source xslSource = new StreamSource(new URL(xslFormString).openStream());
			Source xslSource = new StreamSource(new URL(xslFormString).openStream());
			Transformer transformer = tFactory.newTransformer(xslSource);
			transformer.setParameter("key", objId);
			transformer.transform(xmlSource, new StreamResult(sw));
		}
		catch (Exception e){
			log.error("Exception in system");
			log.error(e.toString());
		}
		return sw.toString();
	}
}
