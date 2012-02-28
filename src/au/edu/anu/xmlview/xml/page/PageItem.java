package au.edu.anu.xmlview.xml.page;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class PageItem {
	private String name;
	private String label;
	private String itemClass;
	private String optionType;
	private String tooltip;
	private String type;
	private String readOnly;
	private String disabled;
	private String value;
	private String referenceValue;
	private String referenceLiteral;
	private List<PageOption> option;
	private List<PageColumn> column;
	
	public PageItem(){
		
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

	@XmlAttribute(name="class")
	public String getItemClass(){
		return itemClass;
	}
	
	public void setItemClass(String itemClass){
		this.itemClass = itemClass;
	}
	
	@XmlAttribute
	public String getOptionType(){
		return optionType;
	}
	
	public void setOptionType(String optionType){
		this.optionType = optionType;
	}

	@XmlAttribute
	public String getTooltip(){
		return tooltip;
	}
	
	public void setTooltip(String tooltip){
		this.tooltip = tooltip;
	}
	
	@XmlAttribute
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}

	@XmlAttribute(name="readonly")
	public String getReadOnly(){
		return readOnly;
	}
	
	public void setReadOnly(String readOnly){
		this.readOnly = readOnly;
	}

	@XmlAttribute
	public String getDisabled(){
		return disabled;
	}
	
	public void setDisabled(String disabled){
		this.disabled = disabled;
	}

	@XmlAttribute
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	@XmlAttribute
	public String getReferenceValue(){
		return referenceValue;
	}
	
	public void setReferenceValue(String referenceValue){
		this.referenceValue = referenceValue;
	}
	
	@XmlAttribute
	public String getReferenceLiteral() {
		return referenceLiteral;
	}

	public void setReferenceLiteral(String referenceLiteral) {
		this.referenceLiteral = referenceLiteral;
	}

	public List<PageOption> getOption(){
		return option;
	}
	
	public void setOption(List<PageOption> option){
		this.option = option;
	}
	
	public List<PageColumn> getColumn(){
		return column;
	}
	
	public void setColumn(List<PageColumn> column){
		this.column = column;
	}
}