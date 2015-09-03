package jac.ws.mtom.server;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;

/**
 * A simple web service server.
 *
 */
public class WebServiceServer {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(WebServiceServer.class);
		String bindingURI = "http://localhost:9898/jac.app/fileService";
		FileTransferer service = new FileTransferer();
		Endpoint.publish(bindingURI, service);
		
		System.out.println("Server started at: " + bindingURI);
		logger.info("Server started at: " + bindingURI);
	}
}