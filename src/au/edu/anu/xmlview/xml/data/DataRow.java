package au.edu.anu.xmlview.xml.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="row")
public class DataRow {
	private List<DataColumn> column;
	
	public DataRow(){
		column = new ArrayList<DataColumn>();
	}
	
	public List<DataColumn> getColumn(){
		return column;
	}
	
	public void setColumn(List<DataColumn> column){
		this.column = column;
	}
}
