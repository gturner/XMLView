package au.edu.anu.xmlview.xml.page;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="webpage")
public class Page {
	List<PageItem> item;
	
	public Page(){
		
	}
	
	public List<PageItem> getItem(){
		return item;
	}
	
	public void setItem(List<PageItem> item){
		this.item = item;
	}
}
