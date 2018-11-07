/**
 * 
 */
package handlers;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.logging.Level;

import cs601.project3.Project3Logger;
import htmlGenerator.CreateContent;
import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;
import slackPack.SlackClient;

/**
 * @author anuragjha
 * ChatHandler class processes the request to Slack and creates the response
 */
public class ChatHandler implements Handlers {

	private Socket socket = null;

	private HTTPRequest request;
	private HTTPResponse response;

	String content = "";

	/**
	 * constructor
	 */
	public ChatHandler() {
		System.out.println("ChatHandler");
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


	@Override
	public void handle(HTTPRequest request, HTTPResponse response) {

		this.request = request;
		this.response = response;
		System.out.println("in handle method of chat handler - request:" + request.toString());
		Project3Logger.write(Level.INFO, "Chat Handler", 0);

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
		System.out.println("in doGET method of chathandler");
		Project3Logger.write(Level.INFO, "Chat Handler - doGET", 0);
		HashMap<String, String> queries = request.getParser().getRequestLineQueries();

		response.setContent(new CreateContent().buildChatForm());

		System.out.println("Content from Chathandler- doGET method is: " + content);
	}


	/**
	 * doPOST method process the post request
	 */
	private void doPOST() {
		System.out.println("in doPOST method of Chat handler");
		Project3Logger.write(Level.INFO, "Review Search Handler - doPOST", 0);
		//working here
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

		if((queriesReqBody != null) && queriesReqBody.containsKey("message")) {
			checkAndSendMessage(queriesReqBody);

		} else if((queriesReqLine != null) && queriesReqLine.containsKey("message")) {
			checkAndSendMessage(queriesReqLine);

		} else {
			response.setContent(new CreateContent().buildContent400());
		}


	}

	/**
	 * checkAndSendMessage post message on slack
	 * @param queries
	 */
	public void checkAndSendMessage(HashMap<String, String> queries) {
		SlackClient slack = null;
		try {
			slack = new SlackClient(URLDecoder.decode(queries.get("message"), "UTF-8"));
			slack.sendMessage();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unable to decode the message");
		}
		if(slack != null) {
			if(slack.isOK()) {
				response.setContent(new CreateContent().buildChatFormAfterSuccess());

			} else {
				response.setContent(new CreateContent().buildChatFormAfterFail());
			}
		}
	}

}
