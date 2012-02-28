package au.edu.anu.xmlview.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author u5125986
 *
 */
public class Validate {
	final Logger log = LoggerFactory.getLogger("SubmitController");
	
	/**
	 * 
	 */
	public Validate(){
	}
	
	/**
	 * Checks whether there is a value in the fields
	 * 
	 * @param values The array of values for the field
	 * @return Whether there is a value in the field
	 */
	public boolean isRequired(String values[]){
		boolean isValid = true;
		if(values == null){
			isValid = false;
		}else if(values.length == 0){
			isValid = false;
		}else if(values[0].trim().equals("")){
			for(int i = 0; isValid == true && i < values.length; i++){
				if(values[i].trim().equals("")){
					isValid = false;
				}
			}
		}
		return isValid;
	}
}
