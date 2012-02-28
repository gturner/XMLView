package au.edu.anu.xmlview.xml.dc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="dc", namespace=DCConstants.OAI_DC)
public class DublinCore {
	private List<String> title;
	private List<String> creator;
	private List<String> subject;
	private List<String> description;
	private List<String> publisher;
	private List<String> contributor;
	private List<String> date;
	private List<String> type;
	private List<String> format;
	private List<String> identifier;
	private List<String> source;
	private List<String> language;
	private List<String> relation;
	private List<String> coverage;
	private List<String> rights;
	
	public DublinCore(){
		title = new ArrayList<String>();
		creator = new ArrayList<String>();
		subject = new ArrayList<String>();
		description = new ArrayList<String>();
		publisher = new ArrayList<String>();
		contributor = new ArrayList<String>();
		date = new ArrayList<String>();
		type = new ArrayList<String>();
		format = new ArrayList<String>();
		identifier = new ArrayList<String>();
		source = new ArrayList<String>();
		language = new ArrayList<String>();
		relation = new ArrayList<String>();
		coverage = new ArrayList<String>();
		rights = new ArrayList<String>();
	}
	
	@XmlElement(namespace=DCConstants.DC)
	public List<String> getTitle(){
		return title;
	}
	
	public void setTitle(List<String> title){
		this.title = title;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getCreator(){
		return creator;
	}
	
	public void setCreator(List<String> creator){
		this.creator = creator;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getSubject(){
		return subject;
	}
	
	public void setSubject(List<String> subject){
		this.subject = subject;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getDescription(){
		return description;
	}
	
	public void setDescription(List<String> description){
		this.description = description;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getPublisher(){
		return publisher;
	}
	
	public void setPublisher(List<String> publisher){
		this.publisher = publisher;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getContributor(){
		return contributor;
	}
	
	public void setContributor(List<String> contributor){
		this.contributor = contributor;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getDate(){
		return date;
	}
	
	public void setDate(List<String> date){
		this.date = date;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getType(){
		return type;
	}
	
	public void setType(List<String> type){
		this.type = type;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getFormat(){
		return format;
	}
	
	public void setFormat(List<String> format){
		this.type = format;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getIdentifier(){
		return identifier;
	}
	
	public void setIdentifier(List<String> identifier){
		this.type = identifier;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getSource(){
		return source;
	}
	
	public void setSource(List<String> source){
		this.source = source;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getLanguage(){
		return language;
	}
	
	public void setLanguage(List<String> language){
		this.language = language;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getRelation(){
		return relation;
	}
	
	public void setRelation(List<String> relation){
		this.relation = relation;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getCoverage(){
		return coverage;
	}
	
	public void setCoverage(List<String> coverage){
		this.coverage = coverage;
	}

	@XmlElement(namespace=DCConstants.DC)
	public List<String> getRights(){
		return rights;
	}
	
	public void setRights(List<String> rights){
		this.rights = rights;
	}
}
