package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import cs601.project1.AmazonDataStore;
import htmlGenerator.CreateContent;
import httpObjects.RequestLineParser;

enum methodsAllowed	{
	GET, POST
}

public class RequestHandler implements Runnable {

	private Socket socket = null;
	private HashMap<String, Handlers> pathMapper;

	private String requestMethod;
	private String requestPath;
	private HashMap<String, String> requestQueries;
	


	public RequestHandler(Socket clientSocket, HashMap<String, Handlers> pathMapper) {
		socket = clientSocket;
		this.pathMapper = pathMapper;
	}


	public void run() {

		System.out.println("working on Client request");
		try(
				BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);
				) {

			//reads the request line - method, query string and http protocol version
			String requestLine = inStream.readLine();
System.out.println("Request Line: " + requestLine);

			//reads the rest of the header
			int contentLength = 0;

			String requestHeader = "";
			String line = inStream.readLine();		 	
			while(line != null && !line.trim().isEmpty()) {
				requestHeader += line + "\n";
				//checking if content length is in request header
				//TODO: fix this messy hack
				////if(this.requestMethod.equals("POST")) {
				if(line.startsWith("Content-Length:")) {
					contentLength = Integer.parseInt(line.split(":")[1].trim());
				}
				/////}
				line = inStream.readLine();
			}
			System.out.println("Headers: \n" + requestHeader);


			//reading body of request /// for getting parameters for post method
			StringBuffer requestBody = new StringBuffer(); // to accumulate result
			if(contentLength > 0) {
				while (inStream.ready()) {
					requestBody.append((char) inStream.read());
				}
				System.out.println("request body: " + requestBody.toString());
			}


			//if(new RequestLineParser(requestLine).checkIfPOST()) {
			//	System.out.println("POST request: " + requestLine);
			//}

			/// get query string for post method


			//RequestLineParser for get the queries
System.out.println("REQUEST LINE: " + requestLine);
			if(requestLine != null)	{
				RequestLineParser requestLineParser = new RequestLineParser(requestLine);
				if(requestLineParser.checkIfGET()) {
					this.requestMethod = "GET";
					this.requestPath = requestLineParser.getRequestLinePath();
					this.requestQueries = requestLineParser.getRequestLineQueries();
				} else if(requestLineParser.checkIfPOST()) {
					this.requestMethod = "POST";
					this.requestPath = requestLineParser.getRequestLinePath();
					this.requestQueries = requestLineParser.getRequestBodyQueries(requestBody.toString());
				} else {
					this.requestMethod = "";
				}
				System.out.println("request: \nMethod: "+this.requestMethod +"Path: " + this.requestPath + "queries: " + this.requestQueries);

				/////////////////////////////ToDo : check client request
				/////////////////////////////ToDo : create a class to utilise RequestLineParser

				//checking client request // and generating response
				String content = "";
				if(this.requestMethod.equals("GET")) {
					//System.out.println("in GET, trying to build content");
					if(this.pathMapper.containsKey(this.requestPath)) {
						if(this.requestPath.equals("/reviewsearch")){ 
							//System.out.println("in reviewSearch, trying to build content");
							content = new CreateContent("/reviewsearch").buildContent();

						}else if(this.requestPath.equals("/find"))	{
							//System.out.println("in find, trying to build content");
							content = new CreateContent("/find").buildContent();
						}

					} else if(this.requestPath.equals("/") && requestQueries==null)	{
						////// ///////// home page !!!!! ========================
						content = new CreateContent("/").buildContent();
						//System.out.println(content);
					} else {
						//generate 404 display///////////////////////////////////////////////
						content = new CreateContent("404").buildContent();
						//System.out.println(content);
					}	
				} else if(this.requestMethod.equals("POST")) { 
					if(this.pathMapper.containsKey(this.requestPath)) {
						if(this.requestPath.equals("/reviewsearch")){ 
							///working here ////*************
							System.out.println("in post request reviewSearch");
							content = new CreateContent("/reviewsearch", this.requestQueries.get("query")).buildContent();
							//AmazonDataStore.ONE.getSearcher().getReviewSearch(this.requestQueries.get("reviewsearch"), outStream);


						} else if(this.requestPath.equals("/find")) {

							System.out.println("in post request findAsin");
							content = new CreateContent("/find", this.requestQueries.get("asin")).buildContent();
							//AmazonDataStore.ONE.getSearcher().getAsinFind(this.requestQueries.get("findasin"), outStream);

						}
					}
				} 
				else {
					//generate 405 display///////////////////////////////////////////////
					content = new CreateContent("405").buildContent();
					//System.out.println(content);

				}

				outStream.write(content);
				//outStream.write(page);
				//outStream.write(outputMsg);
			    outStream.flush();
			}

		}catch(IOException ioe)	{
			ioe.printStackTrace();
		}

	}


}

