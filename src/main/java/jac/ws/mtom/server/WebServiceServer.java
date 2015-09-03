package jac.ws.mtom.server;

import javax.xml.ws.Endpoint;

/**
 * A simple web service server.
 *
 */
public class WebServiceServer {

	public static void main(String[] args) {
		String bindingURI = "http://localhost:9898/jac.app/fileService";
		FileTransferer service = new FileTransferer();
		Endpoint.publish(bindingURI, service);
		System.out.println("Server started at: " + bindingURI);
	}
}