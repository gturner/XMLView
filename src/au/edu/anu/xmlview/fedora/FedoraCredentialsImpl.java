package au.edu.anu.xmlview.fedora;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourmediashelf.fedora.client.FedoraCredentials;

public class FedoraCredentialsImpl {
	final Logger log = LoggerFactory.getLogger("FedoraCredentials");
	FedoraCredentials fedoraCredentials;

	public FedoraCredentialsImpl(){
		fedoraCredentials = null;
		
		Properties properties = new Properties();
		try{
			properties.load(getClass().getResourceAsStream("/fedora.properties"));
			URL baseUrl = new URL(properties.getProperty("fcbaseuri"));
			String username = properties.getProperty("fcuser");
			String password = properties.getProperty("fcpassword");
			fedoraCredentials = new FedoraCredentials(baseUrl, username, password);
		}catch(IOException e){
			log.error("Error reading properties file: " + e.toString());
		}
	}
	
	public FedoraCredentials getFedoraCredentials(){
		return fedoraCredentials;
	}
	
	public void setFedoraCredentials(FedoraCredentials fedoraCredentials){
		this.fedoraCredentials = fedoraCredentials;
	}
}
