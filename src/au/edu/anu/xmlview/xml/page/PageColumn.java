package au.edu.anu.xmlview.xml.page;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="column")
public class PageColumn {
	private String name;
	private String label;
	private String type;
	
	public PageColumn(){
		
	}
	
	@XmlAttribute
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	@XmlAttribute
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}

	@XmlAttribute
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
}
