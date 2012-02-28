package au.edu.anu.xmlview.xml.dc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

public class DCConstants {
	public static final String DC = "http://purl.org/dc/elements/1.1/";
	public static final String OAI_DC = "http://www.openarchives.org/OAI/2.0/oai_dc";
	public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
	/*
	public static final QName DC_TITLE = new QName(DC, "title");
	public static final QName DC_CREATOR = new QName(DC, "creator");
	public static final QName DC_SUBJECT = new QName(DC, "subject");
	public static final QName DC_DESCRIPTION = new QName(DC, "description");
	public static final QName DC_PUBLISHER = new QName(DC, "publisher");
	public static final QName DC_CONTRIBUTOR = new QName(DC, "contributor");
	public static final QName DC_DATE = new QName(DC, "date");
	public static final QName DC_TYPE = new QName(DC, "type");
	public static final QName DC_FORMAT = new QName(DC, "format");
	public static final QName DC_IDENTIFIER = new QName(DC, "identifier");
	public static final QName DC_SOURCE = new QName(DC, "source");
	public static final QName DC_LANGUAGE = new QName(DC, "language");
	public static final QName DC_RELATION = new QName(DC, "relation");
	public static final QName DC_COVERAGE = new QName(DC, "coverage");
	public static final QName DC_RIGHTS = new QName(DC, "rights");
	*/
	/*public static final Map<String, String> TERMS_TO_ELEMENTS;
	static{
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("abstract", "description");
		aMap.put("accessRights", "rights");
		aMap.put("alternative", "title");
		aMap.put("available", "date");
		aMap.put("bibliographicCitation", "identifier");
		aMap.put("conformsTo", "relation");
		TERMS_TO_ELEMENTS = Collections.unmodifiableMap(aMap);
	}*/
	
	public static final Map<String, String> FIELD_TO_ELEMENTS;
	static{
		Map<String, String> aMap = new HashMap<String, String>();
		
		aMap.put("name", "title");
		aMap.put("kind", "type");
		
		
		FIELD_TO_ELEMENTS = Collections.unmodifiableMap(aMap);
	}
}
