package handlers;

import java.util.HashMap;

import htmlGenerator.CreateContent;
import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;
import searchPack.AmazonDataStore;

public class ReviewSearchHandler implements Handlers,Runnable {
	
	private HTTPRequest request;
	private HTTPResponse response;
	
	private String cmdTerm;
	
	public ReviewSearchHandler() {
		
	}
	
	
	@Override
	public void handle(HTTPRequest request, HTTPResponse response) { // remove class/ instance variables // pass around the original
		// objects response and request 
		System.out.println("ReviewSearchHandler - handle");
		this.request = request;
		this.response = response;
		
		
		if(request.getParser().checkIfGET()) { //if method is get
			this.doGET();
		} else if(request.getParser().checkIfPOST()) { //if method is post
			this.doPOST();
		} else	{
			//405 // method not found
			response.setContent(new CreateContent().buildContent405());
		}
		
		
	}
	
	
	private void doGET() {
		HashMap<String, String> queries = request.getParser().getRequestLineQueries();
		//if(queries == null) {
			//String content = new CreateContent("/reviewsearch").buildContent();
			//response.setContent(new CreateContent("/reviewsearch").buildContent());
		response.setContent(new CreateContent().buildReviewSearchForm());
			
		//}
		
	}
	
	
	private void doPOST() {
		HashMap<String, String> queries = request.getParser().getRequestBodyQueries(request.getRequestBody());
		
		if(queries != null && queries.containsKey("query")) {
			//System.out.println("In doPOST  Of ReviewSearchHandler" + queries.get("query"));
			//String content = new CreateContent("/reviewsearch", queries.get("query")).buildContent();
			//response.setContent(new CreateContent("/reviewsearch", queries.get("query")).buildContent());
			response.setContent(new CreateContent().buildReviewSearchResult(queries.get("query")));
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
