/**
 * 
 */
package handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import htmlGenerator.CreateContent;
import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;

/**
 * @author anuragjha
 *
 */
public class RequestHandler1old implements Runnable {

	private Socket socket = null;
	private HashMap<String, Handlers> pathMapper;

	HTTPRequest request;

	HTTPResponse response;

	//String content = "";

	public RequestHandler1old(Socket clientSocket, HashMap<String, Handlers> pathMapper) {
		socket = clientSocket;
		this.pathMapper = pathMapper;
	}


	public void handleRequest() {
		try(
				InputStream instream = socket.getInputStream(); 
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
				) {

			this.readHTTPRequest(instream);

			//			//reads the request line - method, query string and http protocol version
			//			String requestLine = oneLine(instream);
			//			System.out.println("request: " + requestLine);
			//
			//			//reads the rest of the Request Header
			//			String requestHeader = "";
			//			int contentLength = 0; 
			//			if(requestLine != null && !requestLine.trim().isEmpty())	{ // request line is not empty - all logic in this block
			//
			//				String line = oneLine(instream);
			//				while(line != null && !line.trim().isEmpty()) {
			//					requestHeader += line + "\n";
			//					line = oneLine(instream);
			//					//getting contentLength
			//					//TODO: fix this messy hack 
			//					if(line.startsWith("Content-Length:")) {
			//						if(line.split(":").length == 2) {
			//							if(line.split(":")[1].trim().matches("[0-9]+")) {
			//								contentLength = Integer.parseInt(line.split(":")[1].trim());
			//								System.out.println("Content Length: " + contentLength);
			//							}
			//						} else {
			//							System.out.println("Content-Length: Format is not correct, so unable to parse");
			//						}	
			//					}
			//				}
			//				System.out.println("RequestHeader: \n" + requestHeader);
			//
			//				//reading request body if any
			//				int read = 0;
			//				byte[] bytes = null;
			//				if(contentLength > 0) { 
			//
			//					bytes = new byte[contentLength];
			//
			//					read = socket.getInputStream().read(bytes);
			//					while(read < contentLength) {
			//						read += socket.getInputStream().read(bytes, read, (bytes.length-read));
			//					}
			//					System.out.println("Bytes expected: " + contentLength + " Bytes read: " + read);
			//					System.out.println("MESSAGE: " + new String(bytes, StandardCharsets.UTF_8));
			//				}
			//
			//				//once all the request is read create a new request object !!!!!!!!!!!!!
			//				if(bytes != null) {
			//					request = new HTTPRequest(requestLine, requestHeader, new String(bytes, StandardCharsets.UTF_8));
			//				} else	{
			//					request = new HTTPRequest(requestLine, requestHeader, "");
			//				}


			this.createHTTPResponse();
			//				response = new HTTPResponse();
			//
			//				//getting the correct handler
			//				System.out.println("RequestLine Method and Path: " + request.toString());
			//
			//				if(this.pathMapper.containsKey(request.getParser().getRequestLinePath())) {
			//					System.out.println("pathmapper contains the path");
			//					(this.pathMapper.get(request.getParser().getRequestLinePath())).handle(request, response); //
			//
			//				} else {
			//					//404
			//					System.out.println("404 page");
			//					response.setContent(new CreateContent("404").buildContent());
			//				}

			this.writeHTTPresponse(writer);
			//				System.out.println("Response in RequestHandler1: " + response.getContent());
			//				//writing to browser
			//				writer.write(response.getContent());
			//				writer.flush();


			//			} else	{  //requestLine is empty
			//				System.out.println("Empty Request");
			//			}





		}catch(IOException ioe)	{
			System.out.println("IO exception in Request Handler");
		}


	}


	private void readHTTPRequest(InputStream instream) throws IOException {
		//reads the request line - method, query string and http protocol version
		String requestLine = oneLine(instream);
		//request.setRequestLine(oneLine(instream));
		System.out.println("request: " + requestLine);


		//String requestHeader = "";

		if(requestLine != null && !requestLine.trim().isEmpty())	{ //main logic 

			request	= new HTTPRequest(requestLine);
			//reads the rest of the Request Header	
			this.readHTTPRequestHeaders(instream);		

		} else { //requestLine is empty
			System.out.println("Empty Request");

		}

	}


	private void readHTTPRequestHeaders(InputStream instream) throws IOException {

		int contentLength = 0; 
		String requestHeader = "";
		String line = oneLine(instream);
		while(line != null && !line.trim().isEmpty()) {
			requestHeader += line + "\n";
			line = oneLine(instream);
			//getting contentLength
			//TODO: fix this messy hack 
			if(line.startsWith("Content-Length:")) {
				if(line.split(":").length == 2) {
					if(line.split(":")[1].trim().matches("[0-9]+")) {
						contentLength = Integer.parseInt(line.split(":")[1].trim());
						System.out.println("Content Length: " + contentLength);
					}
				} else {
					System.out.println("Content-Length: Format is not correct, so unable to parse");
				}	
			}
		}
		System.out.println("RequestHeader: \n" + requestHeader);
		request.setRequestHeader(requestHeader);

		if(contentLength > 0) {
			this.readHTTPrequestBody(socket, contentLength);
		}
	}






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
		while(b != '\n') { /// check for -1 ///
			bout.write(b);
			b = (byte) instream.read();
			if(b == -1)	{
				break;
			}
		}
		return new String(bout.toByteArray());
	}


	private void createHTTPResponse() {
		response = new HTTPResponse();
		//getting the correct handler
		System.out.println("RequestLine Method and Path: " + request.toString());

		if(this.pathMapper.containsKey(request.getParser().getRequestLinePath())) {
			System.out.println("pathmapper contains the path");
			(this.pathMapper.get(request.getParser().getRequestLinePath())).handle(request, response); //
		} else {
			//404
			System.out.println("404 page");
			response.setContent(new CreateContent("404").buildContent());
		}
	}


	private void writeHTTPresponse(PrintWriter writer) {
		System.out.println("Response in RequestHandler1: " + response.getContent());
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
