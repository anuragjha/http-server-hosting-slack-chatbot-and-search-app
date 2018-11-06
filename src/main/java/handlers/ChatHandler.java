/**
 * 
 */
package handlers;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;

import htmlGenerator.CreateContent;
import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;
import slackPack.SlackClient;

/**
 * @author anuragjha
 *
 */
public class ChatHandler implements Handlers,Runnable {

	private Socket socket = null;
	
	private HTTPRequest request;
	private HTTPResponse response;
	
	String content = "";
	
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	

	public ChatHandler() {
		System.out.println("ChatHandler");
	}
	
	
	@Override
	public void handle(HTTPRequest request, HTTPResponse response) {
		
		this.request = request;
		this.response = response;
		System.out.println("in handle method of chat handler - request:" + request.toString());
		
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
		System.out.println("in doGET method of chathandler");
		HashMap<String, String> queries = request.getParser().getRequestLineQueries();
		//if(queries == null) {
			//System.out.println("inside if condition of doGET method of chathandler");
			//response.setContent(new CreateContent("/slackbot").buildContent());
			response.setContent(new CreateContent().buildChatForm());
		//}
		//content = new CreateContent("/slackbot").buildContent();
		System.out.println("Content from Chathandler- doGET method is: " + content);
	}
	
	
	
	private void doPOST() {
		System.out.println("in doPOST method of chathandler");
		//working here
		HashMap<String, String> queries = request.getParser().getRequestBodyQueries(request.getRequestBody());
		System.out.println("MESsAgE : " + queries.get("message"));
		if((queries.size() >0) && queries.containsKey("message")) {
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
					
					response.setContent(new CreateContent().buildChatFormAfterSuccess());
				}
			}
			
		} else {
			response.setContent(new CreateContent().buildContent400());
		}
	
		//response.setContent(new CreateContent("/slackbot").buildContent());
		
		//
		//TODO : read slack response an create a response
		//
		
		
	}
	
	
	public void run() {
		
	}
	
	
	
}
