package au.edu.anu.xmlview.xml.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class DataMultipleItem {
	private String name;
	private List<DataOption> option;
	private List<DataRow> row;
	
	public DataMultipleItem(){
		option = new ArrayList<DataOption>();
		row = new ArrayList<DataRow>();
	}
	
	@XmlAttribute
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public List<DataOption> getOption(){
		return option;
	}
	
	public void setOption(List<DataOption> option){
		this.option = option;
	}
	
	public List<DataRow> getRow(){
		return row;
	}
	
	public void setRow(List<DataRow> row){
		this.row = row;
	}
}
