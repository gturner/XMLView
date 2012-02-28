package au.edu.anu.xmlview.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import au.edu.anu.xmlview.fedora.FedoraHelper;

@WebServlet(name="saveTemplate",
urlPatterns="/saveTemplate")
public class SaveTemplateController  extends HttpServlet {
	final Logger log = LoggerFactory.getLogger("SaveTemplateController");
	public final static String FS = System.getProperty("file.separator");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Document doc =  getXMLDocument("test.xml");

		PrintWriter out = response.getWriter();
		FedoraHelper fo = new FedoraHelper();
		if(doc != null){
			log.info("Document is not null");
		//	fo.saveExistingObject("test:3", doc);
			out.println("Document has been processed");
		}else{
			log.error("document is null");
			out.println("No document to process");
		}
		out.close();
	}
	
	private Document getXMLDocument(String filename){
		Document doc = null;
		String ctx = getServletContext().getRealPath("") + FS + "resources" + FS;
		
		try{
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			doc = dBuilder.parse(ctx + filename);
		}catch(ParserConfigurationException e){
			log.error(e.toString());
		}catch(IOException e){
			log.error(e.toString());
		}catch(SAXException e){
			log.error(e.toString());
		}
		return doc;
	}
}
