package au.edu.anu.xmlview.xml.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="data")
public class Data {
	List<DataItem> item;
	List<DataMultipleItem> multipleItem;
	
	public Data(){
		item = new ArrayList<DataItem>();
		multipleItem = new ArrayList<DataMultipleItem>();
	}

	@XmlElement(name="item")
	public List<DataItem> getItem(){
		return item;
	}
	
	public void setItem(List<DataItem> item){
		this.item = item;
	}
	
	@XmlElement(name="item")
	public List<DataMultipleItem> getMultipleItem(){
		return multipleItem;
	}
	
	public void setMultipleItem(List<DataMultipleItem> multipleItem){
		this.multipleItem = multipleItem;
	}
}
