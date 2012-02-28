package au.edu.anu.xmlview.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.edu.anu.xmlview.fedora.FedoraHelper;
import au.edu.anu.xmlview.fedora.FedoraReference;
import au.edu.anu.xmlview.util.DatastreamType;
import au.edu.anu.xmlview.validate.Validate;
import au.edu.anu.xmlview.xml.data.Data;
import au.edu.anu.xmlview.xml.data.DataColumn;
import au.edu.anu.xmlview.xml.data.DataItem;
import au.edu.anu.xmlview.xml.data.DataMultipleItem;
import au.edu.anu.xmlview.xml.data.DataOption;
import au.edu.anu.xmlview.xml.data.DataRow;
import au.edu.anu.xmlview.xml.dc.DCConstants;
import au.edu.anu.xmlview.xml.dc.DublinCore2;
import au.edu.anu.xmlview.xml.page.Page;
import au.edu.anu.xmlview.xml.page.PageColumn;
import au.edu.anu.xmlview.xml.page.PageItem;

@WebServlet(name="SubmitController",
urlPatterns="/submitController")
public class SubmitController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public final static String FS = System.getProperty("file.separator");
	
	final Logger log = LoggerFactory.getLogger("SubmitController");
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
			//boolean isValid = validateInput(request);
			boolean isValid = validateInputTest(request);
		if(isValid){
			PrintWriter out = response.getWriter();
			out.println("Testing Submit Controller");
			out.close();
		//	submitDocument();
		}else{
			response.sendRedirect("resources/error.jsp");
		}
	}
	
	private boolean validateInputTest(HttpServletRequest request){
		boolean isValid = true;
		String type = request.getParameter("type");
		String item = request.getParameter("item");
		List<FedoraReference> references = new ArrayList<FedoraReference>();
		DublinCore2 dc = new DublinCore2();
		if(item != null && !item.equals("")){
/*
 * 
				String localpart = DCConstants.FIELD_TO_ELEMENTS.get(name);
				QName qname = new QName(DCConstants.DC, localpart);
				JAXBElement elem = new JAXBElement(qname,String.class,values[0]);
				dc.getItems().add(elem);
 * 
 */
			QName qname = new QName(DCConstants.DC, "identifier");
			JAXBElement elem = new JAXBElement(qname,String.class,item);
			dc.getItems().add(elem);
		}
		FedoraHelper fo = new FedoraHelper();
		
		Page webpage = null;
		
		InputStream layoutStream = null;
		
		if(type != null && !type.equals("")){
		//	log.info("Retreiving xml source: " + type);
			layoutStream = fo.getDatastreamAsStream(type, DatastreamType.XML_SOURCE);
		}else if(item != null && !item.equals("")){
		//	log.info("Retreiving xml template: " + item);
			layoutStream = fo.getDatastreamAsStream(item, DatastreamType.XML_TEMPLATE);
		}
		try{
			JAXBContext jc = JAXBContext.newInstance(Page.class);
			Unmarshaller um = jc.createUnmarshaller();
			webpage = (Page)um.unmarshal(layoutStream);
		}catch(JAXBException e){
			log.error("Error with jaxb: " + e.toString());
		}
		
		if (webpage == null){
			return false;
		}
		
		List<PageItem> itemList = webpage.getItem();
		Data data = new Data();
		for(int i = 0; i < itemList.size(); i++){
			PageItem pageItem = itemList.get(i);
			String optionType = pageItem.getOptionType();
			if(optionType.equals("single")){
				String[] values = request.getParameterValues(pageItem.getName());
				isValid = processValidation(pageItem.getItemClass(), values);
				processSingleItem(data, pageItem.getName(), values, dc);
			}else if(optionType.equals("multiple")){
				String[] values = request.getParameterValues(pageItem.getName());
				isValid = processValidation(pageItem.getItemClass(), values);
				processMultipleItem(data, pageItem.getName(), values, dc);
			}else if(optionType.equals("reference")){
				String[] values = request.getParameterValues(pageItem.getName());
				isValid = processValidation(pageItem.getItemClass(), values);
				processReferenceItem(references, pageItem, values);
				processSingleItem(data, pageItem.getName(), values, dc);
			}else if(optionType.equals("table")){
				processTableItem(data, pageItem, request);
			}
		}
		if(isValid){
			try{
				StringWriter sw = new StringWriter();
				StringWriter dcSW = new StringWriter();
				JAXBContext jc = JAXBContext.newInstance(Data.class);
				Marshaller m = jc.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				m.marshal(data, sw);
				
				if(dc.getItems().size() > 0){
					JAXBContext dcJC = JAXBContext.newInstance(DublinCore2.class);
					Marshaller dcM = dcJC.createMarshaller();
					dcM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					//dcM.setProperty(Marshaller.JAXB_FRAGMENT, true);
					dcM.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd");
					dcM.marshal(dc, dcSW);
					log.info(dcSW.toString());
				}

				if(item != null && !item.equals("")){
					fo.saveExistingObject(item, sw.toString(), references, dc.toString());
				}else if(type != null && !type.equals("")){
					fo.saveNewObject(type, sw.toString(), references, dc.toString());
				}else {
				//	log.info("Parameters aren't defined");
				}
				//log.info(sw.toString());
			}catch(JAXBException e){
				log.error("Error with jaxb: " + e.toString());
			}
		}
		return isValid;
	}
	
	private void processReferenceItem(List<FedoraReference> references, PageItem pageItem, String[] values){
		if(values != null){
			for(int i = 0; i < values.length; i++){
				if(values[i] != null && !values[i].equals("")){
					Boolean isLiteral = new Boolean(false);
					
					if(pageItem.getReferenceLiteral() != null && !pageItem.getReferenceLiteral().equals("")){
						isLiteral = new Boolean(pageItem.getReferenceLiteral());
					}
					FedoraReference fedoraReference = new FedoraReference(pageItem.getReferenceValue(), values[i], isLiteral);
					references.add(fedoraReference);
				}
			}
		}
	}
	
	private boolean processValidation(String classValues, String[] values){
		boolean isValid = true;
		if(classValues != null && !classValues.trim().equals("")){
			String[] validationType = classValues.split(" ");
			Validate validate = new Validate();
			for(int i = 0; i < validationType.length && isValid; i++){
				if(validationType[i].equals("required")){
					isValid = validate.isRequired(values);
				}else if(validationType[i].equals("email")){
					
				}
			}
		}
		return isValid;
	}
	
	private void processSingleItem(Data data, String name, String[] values, DublinCore2 dc){
		if(values != null && !values[0].trim().equals("")){
			DataItem dataItem = new DataItem();
			dataItem.setName(name);
			dataItem.setValue(values[0]);
			data.getItem().add(dataItem);
			if(DCConstants.FIELD_TO_ELEMENTS.containsKey(name)){
				String localpart = DCConstants.FIELD_TO_ELEMENTS.get(name);
				QName qname = new QName(DCConstants.DC, localpart);
				JAXBElement elem = new JAXBElement(qname,String.class,values[0]);
				//dc.getItems().add(elem);
			}
		}
	}
	
	private void processMultipleItem(Data data, String name, String[] values, DublinCore2 dc){
		boolean isDC = false;
		QName qname = null;
		if(values != null){
			if(DCConstants.FIELD_TO_ELEMENTS.containsKey(name)){
				isDC = true;
				String localpart = DCConstants.FIELD_TO_ELEMENTS.get(name);
				qname = new QName(DCConstants.DC, localpart);
			}
			DataMultipleItem dataItem = new DataMultipleItem();
			for(int i = 0; i < values.length; i++){
				if(!values[i].trim().equals("")){
					DataOption option = new DataOption();
					option.setValue(values[i]);
					dataItem.getOption().add(option);
					if(isDC){
						JAXBElement elem = new JAXBElement(qname,String.class,values[i]);
						//dc.getItems().add(elem);
					}
				}
			}
			if(dataItem.getOption().size() > 0){
				dataItem.setName(name);
				data.getMultipleItem().add(dataItem);
			}
		}
	}

	private void processTableItem(Data data, PageItem pageItem, HttpServletRequest request){
		log.info("In Process Table");
		DataMultipleItem dataItem = new DataMultipleItem();
		dataItem.setName(pageItem.getName());
		List<DataRow> row = dataItem.getRow();
		List<PageColumn> columns = pageItem.getColumn();
		String colName = null;
		for(int i = 0; i < columns.size(); i++){
			colName = columns.get(i).getName();
			String[] values = request.getParameterValues(colName);
			for(int j = 0; j < values.length; j++){
				if(row.size() <= j){
					row.add(new DataRow());
				}
				if(!values[j].trim().equals("")){
					DataColumn dataColumn = new DataColumn();
					dataColumn.setName(colName);
					dataColumn.setValue(values[j]);
					row.get(j).getColumn().add(dataColumn);
				}
			}
		}
		for(int i = row.size() - 1; i >= 0; i--){
			log.info("row: " + i);
			if(row.get(i).getColumn().size() == 0){
				row.remove(i);
			}
		}
		if(dataItem.getRow().size() > 0){
			data.getMultipleItem().add(dataItem);
		}
	}
}