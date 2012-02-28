package au.edu.anu.xmlview.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.xmlview.xml.dc.DublinCore;

@WebServlet(name="testXML",
urlPatterns="/testXML")
public class TestXMLController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final Logger log = LoggerFactory.getLogger("SaveTemplateController");
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{

		PrintWriter out = response.getWriter();
		out.println("Testing");
		try{
			JAXBContext context = JAXBContext.newInstance(DublinCore.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			DublinCore dc = new DublinCore();
			List<String> titles = new ArrayList<String>();
			titles.add("Testing Object");
			dc.setTitle(titles);
			List<String> descriptions = new ArrayList<String>();
			descriptions.add("A Description");
			descriptions.add("Description 2");
			dc.setDescription(descriptions);
			
			m.marshal(dc, out);
		}catch(JAXBException e){
			log.error("Exception with jaxb" + e.toString());
		}
		out.close();
	}
}
