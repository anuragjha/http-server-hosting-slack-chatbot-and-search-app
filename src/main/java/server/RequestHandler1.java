/**
 * 
 */
package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import handlers.Handlers;
import htmlGenerator.CreateContent;
import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;
import httpObjects.HttpConstants;

/**
 * @author anuragjha
 *
 */
public class RequestHandler1 implements Runnable {

	private Socket socket = null;
	private HashMap<String, Handlers> pathMapper;

	HTTPRequest request;
	HTTPResponse response;


	public RequestHandler1(Socket clientSocket, HashMap<String, Handlers> pathMapper) {
		socket = clientSocket;
		this.pathMapper = pathMapper;
	}

	/**
	 * handleRequest method reads HTTP request and writes HTTP response
	 */
	public void handleRequest() {
		try(
				InputStream instream = socket.getInputStream(); 
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
				) {

			this.readHTTPRequest(instream);
			if(request != null) {
				this.createHTTPResponse(writer);
			}


		}catch(IOException ioe)	{
			System.out.println("IO exception in Request Handler");
		}


	}

	/**
	 * readHTTPRequestLine method reads HTTP request line
	 * @param instream
	 * @throws IOException
	 */
	private void readHTTPRequest(InputStream instream) throws IOException {
		//reads the request line - method, query string and http protocol version
		//if(instream.available() > 0)	{
		String requestLine = oneLine(instream);
		//request.setRequestLine(oneLine(instream));
		System.out.println("request: " + requestLine);

		if(requestLine != null && !requestLine.trim().isEmpty())	{ //main logic 
			request	= new HTTPRequest(requestLine);
			//reads the rest of the Request Header	
			this.readHTTPRequestHeaders(instream);		

		} else { //requestLine is empty
			//request	= new HTTPRequest();
			System.out.println("Empty Request");

		}
		//}
	}

	/**
	 * readHTTPRequestHeaders method reads HTTP request Headers
	 * @param instream
	 * @throws IOException
	 */
	private void readHTTPRequestHeaders(InputStream instream) throws IOException {

		int contentLength = -1; 
		StringBuffer requestHeader = new StringBuffer();
		String line = oneLine(instream);
		while(line != null && !line.trim().isEmpty()) {
			requestHeader.append(line + "\n");
			line = oneLine(instream);
			//getting contentLength
			if(line.startsWith("Content-Length:")) {
				contentLength = this.checkToGetContentLength(line, contentLength);
			}
		}
		System.out.println("RequestHeader: \n" + requestHeader.toString());
		request.setRequestHeader(requestHeader.toString());

		if(contentLength > 0) {
			this.readHTTPrequestBody(socket, contentLength);
		}
	}

	/**
	 * checkToGetContentLength method finds the Content-Length value in the request header 
	 * @param line
	 * @param contentLength
	 * @return
	 */
	private int checkToGetContentLength(String line, int contentLength) {
		if(contentLength != -1) {
			System.out.println("Content-Length encountered for second time - content-Length made 0 now");
			return 0;
		}

		if(line.split(":").length == 2) {
			if(line.split(":")[1].trim().matches("[0-9]+")) {
				contentLength = Integer.parseInt(line.split(":")[1].trim());
				System.out.println("Content Length: " + contentLength);

			}
		} else {
			System.out.println("Content-Length: Format is not correct, so unable to parse");
		}	

		if(contentLength >= 0) {
			return contentLength;
		} else {
			return 0;
		}

	}

	/**
	 * readHTTPrequestBody method reads HTTP request Body
	 * @param socket
	 * @param contentLength
	 * @throws IOException
	 */
	private void readHTTPrequestBody(Socket socket, int contentLength) throws IOException {
		//reading request body if any
		int read = 0;
		byte[] bytes = null;

		bytes = new byte[contentLength];
		//
		read = socket.getInputStream().read(bytes);
		while(read < contentLength) {
			read += socket.getInputStream().read(bytes, read, (bytes.length-read));
		}
		System.out.println("Bytes expected: " + contentLength + " Bytes read: " + read);
		System.out.println("MESSAGE: " + new String(bytes, StandardCharsets.UTF_8));

		request.setRequestBody(new String(bytes, StandardCharsets.UTF_8));


	}


	/**
	 * Read a line of bytes until \n character or until end of input stream.
	 * @param instream
	 * @return
	 * @throws IOException
	 */
	private static String oneLine(InputStream instream) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte b = (byte) instream.read();
		while(b != '\n' && b != -1) { /// check for -1 ///
			bout.write(b);
			b = (byte) instream.read();
			//if(b == -1)	{
			//	break;
			//}
		}
		return new String(bout.toByteArray());
	}

	/**
	 * createHTTPResponse creates and writes the HTTP response
	 * @param writer
	 */
	private void createHTTPResponse(PrintWriter writer) {
		response = new HTTPResponse();
		//getting the correct handler
		System.out.println("RequestLine Method and Path: " + request.toString());

		//if correct method
		if(!(request.getParser().checkIfGET()) &&
				!(request.getParser().checkIfPOST())) {  //method not found 405
			response.setContent(new CreateContent().buildContent405()); 
		
		} else { //method found

			if(this.pathMapper.containsKey(request.getParser().getRequestLinePath())) { //path found
				System.out.println("pathmapper contains the path");
				(this.pathMapper.get(request.getParser().getRequestLinePath())).handle(request, response); //
			} else {  //path not found
				//404
				System.out.println("404 page");
				response.setContent(new CreateContent().buildContent404()); ////change this !!!!!
			}

		}


		//		if(this.pathMapper.containsKey(request.getParser().getRequestLinePath())) {
		//			System.out.println("pathmapper contains the path");
		//			(this.pathMapper.get(request.getParser().getRequestLinePath())).handle(request, response); //
		//		} else {
		//			//404
		//			System.out.println("404 page");
		//			response.setContent(new CreateContent().buildContent404()); ////change this !!!!!
		//		}

		//System.out.println("Response in RequestHandler1: " + response.getContent());
		//writing to browser
		writer.write(response.getContent());
		writer.flush();
	}


	@Override
	public void run() {
		System.out.println("Running RequestHandler 1");
		this.handleRequest();

	}


}
