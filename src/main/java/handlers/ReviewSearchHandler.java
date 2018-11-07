package handlers;

import java.util.HashMap;
import java.util.logging.Level;

import org.apache.commons.lang3.StringEscapeUtils;

import cs601.project3.Project3Logger;
import htmlGenerator.CreateContent;
import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;

/**
 * @author anuragjha
 * FindHandler class processes the request regarding asin to AmazonDataStore and creates a response
 */
public class ReviewSearchHandler implements Handlers {

	private HTTPRequest request;
	private HTTPResponse response;

	private String cmdTerm;

	/**
	 * constructor
	 */
	public ReviewSearchHandler() {

	}


	@Override
	public void handle(HTTPRequest request, HTTPResponse response) {
		System.out.println("ReviewSearchHandler - handle");
		Project3Logger.write(Level.INFO, "Review Search Handler", 0);

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

	/**
	 * doGET method process the get request
	 */
	private void doGET() {
		Project3Logger.write(Level.INFO, "Review Search Handler - doGET", 0);
		HashMap<String, String> queries = request.getParser().getRequestLineQueries();

		response.setContent(new CreateContent().buildReviewSearchForm());
	}


	/**
	 * doPOST method process the post request
	 */
	private void doPOST() {
		Project3Logger.write(Level.INFO, "Review Search Handler - doPOST", 0);
		HashMap<String, String> queriesReqBody = request.getParser().getRequestBodyQueries(request.getRequestBody());
		HashMap<String, String> queriesReqLine = request.getParser().getRequestLineQueries();

		if(queriesReqBody != null && queriesReqBody.size() > 0) {
			Project3Logger.write(Level.INFO, "Queries : ", 0);
			for(String query : queriesReqBody.keySet()) {
				Project3Logger.write(Level.INFO, query + " : " + queriesReqBody.get(query), 0);
			}
		}

		if(queriesReqLine != null && queriesReqLine.size() > 0) {
			Project3Logger.write(Level.INFO, "Queries : ", 0);
			for(String query : queriesReqLine.keySet()) {
				Project3Logger.write(Level.INFO, query + " : " + queriesReqLine.get(query), 0);
			}
		}


		if(queriesReqBody != null && queriesReqBody.containsKey("query")) {
			response.setContent((new CreateContent().buildReviewSearchResult(queriesReqBody.get("query"))));

		} else if(queriesReqLine != null && queriesReqLine.containsKey("query")) {
			response.setContent((new CreateContent().buildReviewSearchResult(queriesReqLine.get("query"))));

		} else {
			response.setContent(new CreateContent().buildContent400());
		}

	}


	public static void main(String[] args)	{
		ReviewSearchHandler r = new ReviewSearchHandler();

	}


}
