/**
 * 
 */
package httpObjects;

import java.util.HashMap;

import htmlGenerator.CreateContent;

/**
 * @author anuragjha
 * HTTPResponse stores the HTTP response
 */
public class HTTPResponse {


	private StringBuilder responseHeader;
	private StringBuilder responseBody;
	private HashMap<String,String> queries;

	private String responseType;

	public CreateContent createContent;

	public String content = "";

	public HTTPResponse() {
		createContent = new CreateContent();
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}


	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}


}


