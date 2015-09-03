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

import org.apache.log4j.Logger;


/**
 * A web service endpoint implementation that demonstrates the usage of
 * MTOM (Message Transmission Optimization Mechanism).
 *
 */
@WebService
@MTOM(enabled = true, threshold = 10240)
public class FileTransferer {
	
	
	Logger logger = Logger.getLogger(getClass());
	Properties prop = new Properties();
	private final UnZip unZipper = new UnZip();
	Boolean success = false;
	
	@WebMethod
	public boolean upload(String fileName, byte[] imageBytes) {
		InputStream input = null;
		String filePath = null;
		String folderPath = null;
		//load a properties file from class path, inside static method
		try {
			String filename = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			prop.load(input);
			folderPath = prop.getProperty("uploaded.files.folder");
			filePath = folderPath + fileName;
		} catch (IOException e) {
			logger.error("Unable to establish the Uploaded files folder", e);
			e.printStackTrace();
		}

		if (filePath!=null){
			try {
				FileOutputStream fos = new FileOutputStream(filePath);
				BufferedOutputStream outputStream = new BufferedOutputStream(fos);
				outputStream.write(imageBytes);
				outputStream.close();
				
				logger.info("Received file: " + filePath);
				unzipFileAndCheck(filePath, folderPath);
				return success;
			} catch (IOException ex) {
				logger.error("Error receiving sent file ", ex);
				throw new WebServiceException(ex);
			}
		}
		else {
			
			throw new WebServiceException("No destiny filepath defined");
		}
	
	}

	private void unzipFileAndCheck (String filePath, String outputFolder){
		
		try {
			unZipper.unZipIt(filePath, outputFolder);
			success = true;
		} catch (IOException e) {
			logger.error("ERROR extracting files from zip File "+ filePath, e);
			e.printStackTrace();
		}
	}
	
	
	
	
}