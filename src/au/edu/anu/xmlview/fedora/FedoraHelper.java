package au.edu.anu.xmlview.fedora;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.yourmediashelf.fedora.client.FedoraClient;
import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.request.AddDatastream;
import com.yourmediashelf.fedora.client.request.AddRelationship;
import com.yourmediashelf.fedora.client.request.FindObjects;
import com.yourmediashelf.fedora.client.request.GetDatastreamDissemination;
import com.yourmediashelf.fedora.client.request.GetObjectXML;
import com.yourmediashelf.fedora.client.request.Ingest;
import com.yourmediashelf.fedora.client.request.ModifyDatastream;
import com.yourmediashelf.fedora.client.request.PurgeDatastream;
import com.yourmediashelf.fedora.client.response.AddDatastreamResponse;
import com.yourmediashelf.fedora.client.response.FedoraResponse;
import com.yourmediashelf.fedora.client.response.FindObjectsResponse;
import com.yourmediashelf.fedora.client.response.IngestResponse;
import com.yourmediashelf.fedora.client.response.ModifyDatastreamResponse;
import com.yourmediashelf.fedora.client.response.PurgeDatastreamResponse;

public class FedoraHelper {
	final Logger log = LoggerFactory.getLogger(FedoraHelper.class);
	FedoraClient fc;
	
	public FedoraHelper(){
		fc = new FedoraClient(new FedoraCredentialsImpl().getFedoraCredentials());
	}
	
	public void saveNewObject(String templateID, String sourceXMLDoc, List<FedoraReference> references, String dc){
		log.info("In submit document");
		
		String pid = "";
		String template = "http://localhost:8380/fedora/objects/" + templateID + "/datastreams/XML_SOURCE/content";
		//String template = "fedora:info/" + templateID;
		try{
			//create a new object in fedora
			IngestResponse ingestResponse = new Ingest().namespace("test").execute(fc);
		
			pid = ingestResponse.getPid();
			if(!pid.equals("")){
				//Add the xml source
				AddDatastreamResponse sourceResponse = new AddDatastream(pid, "XML_SOURCE").controlGroup("X").dsLabel("XML Document").content(sourceXMLDoc).mimeType(MediaType.TEXT_XML).execute(fc);
				
				//Add the template to the objects
				AddDatastreamResponse templateResponse = new AddDatastream(pid, "XML_TEMPLATE").controlGroup("M").dsLabel("XML Template").dsLocation(template).mimeType("text/xml").execute(fc);
				
				addRelationships(pid, references);
				//addDublinCore(pid, dc);
			}
		}catch(FedoraClientException e){
			log.error(e.toString());
		}
	}
	
	private static URI getBaseURI(){
		return UriBuilder.fromUri("http://localhost:8380").build();
	}
	
	private void addRelationships(String pid, List<FedoraReference> references)
			throws FedoraClientException{
		log.info("PID: " + pid);
		references.add(new FedoraReference("info:fedora/fedora-system:def/model#hasModel", "info:fedora/def:DCDataContentModel", false));
		references.add(new FedoraReference("http://www.openarchives.org/OAI/2.0/itemID", "oai:" + pid, false));
		
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		//client.addFilter(new HTTPBasicAuthFilter("fedoraAdmin", "fedoraAdmin"));
		WebResource service = client.resource(getBaseURI());
		
		PurgeDatastreamResponse purgeDataResponse = new PurgeDatastream(pid,"RELS-EXT").execute(fc);
		
		for(int i = 0; i < references.size(); i++){
			FedoraReference reference = references.get(i);
			FedoraResponse relResponse = new AddRelationship(pid).predicate(reference.getPredicate_()).object(reference.getObject_()).isLiteral(reference.isLiteral_()).execute(fc);
		}
	}
	
	private void addSingleReferenceItem(Map<String, List<String>> references, String referenceKey, String referenceValue){
		List<String> items = new ArrayList<String>();
		items.add(referenceValue);
		if(references.containsKey(referenceKey)){
			references.get(referenceKey).addAll(items);
		}else{
			references.put(referenceKey, items);
		}
	}
	
	private void addDublinCore(String pid, String dc) throws FedoraClientException {
		ModifyDatastreamResponse sourceResponse = new ModifyDatastream(pid, "DC").content(dc).execute(fc);
	}

	public void saveExistingObject(String pid, String sourceXMLDoc, List<FedoraReference> references, String dc){
		try{
			ModifyDatastreamResponse sourceResponse = new ModifyDatastream(pid, "XML_SOURCE").dsLabel("XML Document").content(sourceXMLDoc).execute(fc);
			
			addRelationships(pid, references);
			
			//addDublinCore(pid, dc);
		}catch(FedoraClientException e){
			log.error(e.toString());
		}
	}
	
	public Document getXMLDatastreamObject(String pid, String dsId){
		Document doc = null;
		try{
			FedoraResponse sourceResponse = new GetDatastreamDissemination(pid, dsId).execute(fc);
			doc = sourceResponse.getEntity(Document.class);
		}catch(FedoraClientException e){
			log.error(e.toString());
		}
		
		return doc;
	}
	
	public InputStream getDatastreamAsStream(String pid, String dsId){
		InputStream is = null;
		try{
			FedoraResponse sourceResponse = new GetDatastreamDissemination(pid, dsId).execute(fc);
			is = sourceResponse.getEntityInputStream();
		}catch(FedoraClientException e){
			log.error(e.toString());
		}
		return is;
	}
	/*
	public List<String> findObjects(){
		List<String> aList = null;
		try{
			String query = "pid~test*";
			FindObjectsResponse sourceResponse = new FindObjects().query(query).pid().label().execute(fc);
			InputStream doc = sourceResponse.getEntity(InputStream.class);
			aList = sourceResponse.getPids();
		}catch(FedoraClientException e){
			log.error(e.toString());
		}
		return aList;
	}
	*/
	public FindObjectsResponse findObjects(){
		//InputStream is = null;
		FindObjectsResponse sourceResponse = null;
		try{
			String query = "pid~test*";
			sourceResponse = new FindObjects().query(query).maxResults(1000).pid().title().resultFormat("xml").execute(fc);
			//is = sourceResponse.getEntity(InputStream.class);
			//sourceResponse.
			//sourceResponse.
			//aList = sourceResponse.getPids();
		}catch(FedoraClientException e){
			log.error(e.toString());
		}
		return sourceResponse;
	}
	
	public InputStream getObjectXML(String pid){
		InputStream is = null;
		try{
			FedoraResponse sourceResponse = new GetObjectXML(pid).execute(fc);
			is = sourceResponse.getEntityInputStream();
		}catch(FedoraClientException e){
			log.error(e.toString());
		}
		return is;
	}
	
}
