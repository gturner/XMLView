package au.edu.anu.xmlview.xml.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="option")
public class DataOption {
	private String value;
	
	@XmlValue
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
}
