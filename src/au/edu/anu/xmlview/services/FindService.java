package au.edu.anu.xmlview.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.xmlview.fedora.FedoraHelper;

import com.yourmediashelf.fedora.client.FedoraClientException;
import com.yourmediashelf.fedora.client.response.FindObjectsResponse;

@Path("/find")
public class FindService {
	final Logger log = LoggerFactory.getLogger(FindService.class);
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String findHTML(){
		StringBuffer output = new StringBuffer();
		//return "<html><body>Testing Find</body></html>";
	//	StringBuffer sb = new StringBuffer();
		FedoraHelper fh = new FedoraHelper();
		
		FindObjectsResponse fedoraResponse = fh.findObjects();
		List<String> pids = fedoraResponse.getPids();
		output.append("<html>");
		output.append("<body>");
		output.append("Results<br />");
		output.append("<table>");
		try{
			for(int i = 0; i < pids.size(); i++){
				output.append("<tr>");
				output.append("<td>");
				List<String> aList = fedoraResponse.getObjectField(pids.get(i), "pid");
				for(int j = 0; j < aList.size(); i++){
					output.append(fedoraResponse.getObjectField(pids.get(i), "pid"));
				}
				output.append("</td>");
				output.append("<td>");
				output.append(fedoraResponse.getObjectField(pids.get(i), "title"));
				//fedoraResponse.getObjectField(pids.get(i), "label");
				output.append("</td>");
				output.append("<tr>");
				
		}
		}catch(FedoraClientException e){
			log.info("Exception: " + e.toString());
		}
		output.append("</table>");
		
		output.append("</body>");
		output.append("<html>");
		
		return output.toString();
	}
	
/*
	@GET
	@Produces(MediaType.TEXT_XML)
	public String findXML(){
		
		return "Text";
	}
	
	@GET
	@Path("{text}")
	@Produces(MediaType.TEXT_HTML)
	public String findSomething(@QueryParam("something") String param1){
		return "";
	}
	
	private String printList(){
		
	}*/
}
