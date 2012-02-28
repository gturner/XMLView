package au.edu.anu.xmlview.util;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class Util {
	public static String NS = "http://anu.edu.au/object/";
	final static Logger log = LoggerFactory.getLogger("Util");
	
	public static String printDocument(Document doc){
		String output = "";
		
		try{
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString();//.replaceAll("\n|\r", "");
		}catch(TransformerConfigurationException e){
			log.error(e.toString());
		}catch(TransformerException e){
			log.error(e.toString());
		}
		
		return output;
	}
	
	public static String printDocument(InputStream is){
		String output = "";
		try{
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			Source source = new StreamSource(is);
			StringWriter writer = new StringWriter();
			transformer.transform(source, new StreamResult(writer));
			output = writer.getBuffer().toString();//.replaceAll("\n|\r", "");
		}catch(TransformerConfigurationException e){
	//		log.error(e.toString());
		}catch(TransformerException e){
	//		log.error(e.toString());
		}
		return output;
	}
}
