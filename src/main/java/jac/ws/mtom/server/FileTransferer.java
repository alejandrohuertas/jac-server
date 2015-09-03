package jac.ws.mtom.server;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.MTOM;


/**
 * A web service endpoint implementation that demonstrates the usage of
 * MTOM (Message Transmission Optimization Mechanism).
 *
 */
@WebService
@MTOM(enabled = true, threshold = 10240)
public class FileTransferer {
	
	Properties prop = new Properties();
	
	@WebMethod
	public void upload(String fileName, byte[] imageBytes) {
		InputStream input = null;
		String filePath = null;
		
		//load a properties file from class path, inside static method
		try {
			String filename = "config.properties";
			input = FileTransferer.class.getClassLoader().getResourceAsStream(filename);
			prop.load(input);
			String folderPath = prop.getProperty("uploaded.files.folder");
			filePath = folderPath + fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (filePath!=null){
			try {
				FileOutputStream fos = new FileOutputStream(filePath);
				BufferedOutputStream outputStream = new BufferedOutputStream(fos);
				outputStream.write(imageBytes);
				outputStream.close();
				
				System.out.println("Received file: " + filePath);
				
			} catch (IOException ex) {
				System.err.println(ex);
				throw new WebServiceException(ex);
			}
		}
		else {
			
			throw new WebServiceException("No destiny filepath defined");
		}
	
	}
	
	
}