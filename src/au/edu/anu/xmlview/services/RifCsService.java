package au.edu.anu.xmlview.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.edu.anu.xmlview.fedora.FedoraHelper;
import au.edu.anu.xmlview.fedora.SolrHelper;
import au.edu.anu.xmlview.util.DatastreamType;

import com.yourmediashelf.fedora.client.response.FindObjectsResponse;

@Path("rifcs")
public class RifCsService {
	final Logger log = LoggerFactory.getLogger(RifCsService.class);
	private static final String ctx = "http://localhost:9380/XMLView/resources/";
	private static final String saveLoc = "C:/WorkSpace/Testing/rifcs.xml";
	
	@Context UriInfo uriInfo;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_XML)
	public String getRifCs(@PathParam("id") String objId){
		log.info("Id: " + objId);
		StringWriter sw = new StringWriter();
		FedoraHelper fo = new FedoraHelper();
		
		InputStream xmlFormStream = fo.getDatastreamAsStream(objId, DatastreamType.XML_SOURCE);
		String xslFormString = ctx + "rifcs.xsl";
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
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public String getRifCsList(){
		FedoraHelper fh = new FedoraHelper();
		FindObjectsResponse fedoraResponse = fh.findObjects();
		StringBuffer sb = new StringBuffer();
		List<String> pids = fedoraResponse.getPids();
		sb.append("<result>");
		if(pids.size() > 0){
			for(int i = 0; i < pids.size(); i++){
				sb.append("<pid>");
				sb.append(pids.get(i));
				sb.append("</pid>");
			}
		}
		else{
			sb.append("No PIDS");
		}
		sb.append("</result>");
		File file = new File("C:/WorkSpace/Testing/rifcs.xml");

		try{
			OutputStream os = null;
			String additionalInfo = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?>";

			os = new FileOutputStream(file);
			os.write(additionalInfo.getBytes(Charset.forName("UTF-8")));
			additionalInfo = "<registryObjects>";
			os.write(additionalInfo.getBytes(Charset.forName("UTF-8")));

			SolrHelper sh = new SolrHelper();
			InputStream shInputStream = sh.getItemsById("item.kind","collection");
			addObjectsToOutput(shInputStream, os);

			shInputStream = sh.getItemsById("item.kind","activity");
			addObjectsToOutput(shInputStream, os);

			shInputStream = sh.getItemsById("item.kind","party");
			addObjectsToOutput(shInputStream, os);

			shInputStream = sh.getItemsById("item.kind","service");
			addObjectsToOutput(shInputStream, os);
			
			additionalInfo = "</registryObjects>";
			os.write(additionalInfo.getBytes(Charset.forName("UTF-8")));
			os.close();
		}
		catch(FileNotFoundException e){
			log.info("File not found exception: " + e.toString());
		}
		catch(MalformedURLException e){
			log.info("Exception with url string: " + e.toString());
		}
		catch(IOException e){
			log.info("Exception reading/writing file: " + e.toString());
		}
		catch(TransformerException e){
			log.info("Exception transforming xml: " + e.toString());
		}
		catch(ParserConfigurationException e){
			log.info(e.toString());
		}
		catch(SAXException e){
			log.info(e.toString());
		}
		catch(XPathExpressionException e){
			log.info(e.toString());
		}
		
		return sb.toString();
	}
	
	private void addObjectsToOutput(InputStream is, OutputStream os)
			throws MalformedURLException,TransformerException,IOException,
			ParserConfigurationException, SAXException, XPathExpressionException {
		log.info("Add objects to output");
		FedoraHelper fh = new FedoraHelper();
		List<String> pids = getPidList(is);
		
		
		String xslFormString = ctx + "rifcs.xsl";
		InputStream xmlFormStream = null;

		Source xslSource = new StreamSource(new URL(xslFormString).openStream());
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(xslSource);
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		
		if(pids.size() > 0){
			for(int i = 0; i < pids.size(); i++){
				xmlFormStream = fh.getDatastreamAsStream(pids.get(i), DatastreamType.XML_SOURCE);
				if(xmlFormStream != null){
					Source xmlSource = new StreamSource(xmlFormStream);
					transformer.setParameter("key", pids.get(i));
					transformer.transform(xmlSource, new StreamResult(os));
				}
			}
		}
	}
	
	private List<String> getPidList(InputStream is)
		throws IOException, ParserConfigurationException, SAXException, XPathExpressionException{
		log.info("Get PID List");
		List<String> pids = new ArrayList<String>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();
		XPathExpression expr = xpath.compile("/response/result/doc/str[@name='id']");
		NodeList nodeList = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
		//log.info("Evaluation: " + stuff);
		log.info("Node List Length: " + nodeList.getLength());
		for(int i = 0; i < nodeList.getLength(); i++){
			//log.info(arg0)
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element elem = (Element)node;
				pids.add(elem.getTextContent());
			}
		}
		
		return pids;
	}
}
