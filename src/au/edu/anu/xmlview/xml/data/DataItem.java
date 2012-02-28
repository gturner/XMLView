package au.edu.anu.xmlview.xml.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="item")
public class DataItem {
	private String name;
	private String value;
	
	public DataItem(){
	}
	
	@XmlAttribute
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@XmlValue
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
}
