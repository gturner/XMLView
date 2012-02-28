package au.edu.anu.xmlview.xml.dc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dc", namespace=DCConstants.OAI_DC)
public class DublinCore2 {
	private String schemaLocation;
	List<JAXBElement<String>> items;
	
	public DublinCore2(){
		items = new ArrayList<JAXBElement<String>>();
	//	schemaLocation = "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd";
	}
	
	@XmlAnyElement
	public List<JAXBElement<String>> getItems(){
		return items;
	}
	
	public void setItems(List<JAXBElement<String>> items){
		this.items = items;
	}
}
