package au.edu.anu.xmlview.xml.page;

import javax.xml.bind.annotation.XmlAttribute;

public class PageOption {
	private String label;
	private String value;
	
	public PageOption(){
		
	}
	
	@XmlAttribute
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}

	@XmlAttribute
	public String getValue(){
		return label;
	}
	
	public void setValue(String value){
		this.value = value;
	}
}
