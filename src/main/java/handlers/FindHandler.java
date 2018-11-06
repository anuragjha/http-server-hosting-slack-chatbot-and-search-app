/**
 * 
 */
package handlers;

import java.util.HashMap;

import htmlGenerator.CreateContent;
import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;

/**
 * @author anuragjha
 *
 */
public class FindHandler implements Handlers,Runnable {
		
		private HTTPRequest request;
		private HTTPResponse response;
		
		private String cmdTerm;
		
		public FindHandler() {
			
		}
		
		
		@Override
		public void handle(HTTPRequest request, HTTPResponse response) { // remove class/ instance variables 
																		// pass around the original
																		// objects response and request 
			System.out.println("FindHandler - handle");
			this.request = request;
			this.response = response;
			
			
			if(request.getParser().checkIfGET()) { //if method is get
				this.doGET();
			} else if(request.getParser().checkIfPOST()) { //if method is post
				this.doPOST();
			} else	{
				//405 // method not found
				//String content = new CreateContent("405").buildContent();
				response.setContent(new CreateContent().buildContent405());
			}
			
			
		}
		
		
		private void doGET() {
			HashMap<String, String> queries = request.getParser().getRequestLineQueries();
			//if(queries == null) {
				//String content = new CreateContent("/find").buildContent();
				//response.setContent(new CreateContent("/find").buildContent());
				response.setContent(new CreateContent().buildFindForm());
		  //}
			
		}
		
		
		private void doPOST() {
			HashMap<String, String> queries = request.getParser().getRequestBodyQueries(request.getRequestBody());
			
			if(queries != null && queries.containsKey("asin")) {
				//System.out.println("In doPOST  Of FindHandler" + queries.get("find"));
				//String content = new CreateContent("/find", queries.get("find")).buildContent();
				//response.setContent(new CreateContent("/find", queries.get("asin")).buildContent());
				response.setContent(new CreateContent().buildFindResult(queries.get("asin")));
			} else {
				response.setContent(new CreateContent().buildContent400());
			}
			
		}
		
		
		public void run()	{
			
			
		}	
		
		
		public static void main(String[] args)	{
			ReviewSearchHandler r = new ReviewSearchHandler();
			
		}
		
		
	}

