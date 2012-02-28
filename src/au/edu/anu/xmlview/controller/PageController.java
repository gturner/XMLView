package au.edu.anu.xmlview.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import au.edu.anu.xmlview.fedora.FedoraHelper;
import au.edu.anu.xmlview.util.DatastreamType;

/**
 * A controller that creates a web page based on input xml files for which their flow is determined by the output xsl file
 * @author u5125986
 *
 */
@WebServlet(name="pageController",
urlPatterns="/pageController")
public class PageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	final Logger log = LoggerFactory.getLogger("PageController");
	//final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("PageController");
	
	public final static String FS = System.getProperty("file.separator"); 
	
	/**
	 * Returns a web page
	 * 
	 * @param request The servlet request action
	 * @param response The servlet response action
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Document doc = null;
		Document paramDoc = null;
		
		String layout = request.getParameter("layout");
		String item = request.getParameter("item");
		String type = request.getParameter("type");
		try{
			paramDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		}catch(ParserConfigurationException e){
			log.error("Unable to create document");
		}
		
		FedoraHelper fo = new FedoraHelper();
		String xslId = getXSLId(layout);
		if(xslId.equals("")){
			log.error("No layout specified");
			out.println("Error retrieving layout");
			out.close();
			return;
		}
		InputStream xmlFormStream = null;
		if(item != null && !item.equals("")){
			xmlFormStream = fo.getDatastreamAsStream(item, DatastreamType.XML_TEMPLATE);
		}else if(type != null && !type.equals("")){
			xmlFormStream = fo.getDatastreamAsStream(type, DatastreamType.XML_SOURCE);
		}else{
			log.error("No item or type");
			out.println("Error retrieving layout");
			out.close();
			return;
		}
		String ctx = getServletContext().getRealPath("") + FS + "resources" + FS;
		String xslFormString = ctx + "testform.xsl";
		/*
		 * InputStream xslFormStream = fo.getDatastreamAsStream(xslId, DatastreamType.XSL_SOURCE);
		 * 
		 */
		try{
			if(item != null && !item.equals("")){
				doc = fo.getXMLDatastreamObject(item, "XML_SOURCE");
			}
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Source xmlSource = new StreamSource(xmlFormStream);
			//Source xslSource = new StreamSource(new URL(xslFormString).openStream());
			Source xslSource = new StreamSource(new URL("file", "", xslFormString).openStream());
			Transformer transformer = tFactory.newTransformer(xslSource);
			
			if(doc != null){
				transformer.setParameter("param1", doc);
			}
			if(item != null && !item.equals("")){
				transformer.setParameter("item", item);
			}
			if(type != null && !type.equals("")){
				transformer.setParameter("type", type);
			}
			transformer.transform(xmlSource, new StreamResult(out));
		}catch (Exception e){
			out.write("Exception requesting page");
			log.error("Exception in system");
			log.error(e.toString());
		}finally{
			out.close();
		}
	}
	
	/**
	 * 
	 * @param layout The layout type to get the object id for
	 * @return The object id of the layout
	 */
	private String getXSLId(String layout){
		String url = "";
		if(layout == null){
			url = "";
		}else if(layout.equals("test")){
			url = "test:2";
		}else if(layout.equals("display")){
			url = "";
		}else{
			url = "";
		}
		return url;
	}
}
