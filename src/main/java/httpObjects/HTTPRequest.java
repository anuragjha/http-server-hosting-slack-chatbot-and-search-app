/**
 * 
 */
package httpObjects;

import java.util.HashMap;

/**
 * @author anuragjha
 * HTTPRequest class stores the HTTP request
 */
public class HTTPRequest {

	private String requestLine;
	private String requestHeader;
	private String requestBody;

	private RequestLineParser parser; 

	private String requestMethod = "";
	private String requestPath = "";
	private HashMap<String, String> requestQueries = new HashMap<String, String>();

	/*
	 * constructor
	 */
	public HTTPRequest(String requestLine) {
		this.requestLine = requestLine;
		this.parser = new RequestLineParser(this.requestLine);
		this.requestHeader = "";
		this.requestBody = "";
	}


	@Override
	public String toString() {
		StringBuffer toString = new StringBuffer();
		if(requestLine.length() > 0) {
			if(requestHeader.length() > 0) {
				if(requestBody.length() > 0) {
					return "HTTPRequest: " + requestLine + "\n" + requestHeader + "\n" + requestBody;
				}
				return "HTTPRequest: " + requestLine + "\n" + requestHeader;
			}
			return "HTTPRequest: " + requestLine;
		}
		return "HTTPRequest: empty";
	}


	/**
	 * @return the parser
	 */
	public RequestLineParser getParser() {
		return parser;
	}

	/**
	 * @return the requestLine
	 */
	public String getRequestLine() {
		return requestLine;
	}

	/**
	 * @param requestLine the requestLine to set
	 */
	public void setRequestLine(String requestLine) {
		this.requestLine = requestLine;
	}

	/**
	 * @return the requestHeader
	 */
	public String getRequestHeader() {
		return requestHeader;
	}

	/**
	 * @param requestHeader the requestHeader to set
	 */
	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	/**
	 * @return the requestBody
	 */
	public String getRequestBody() {
		return requestBody;
	}

	/**
	 * @param requestBody the requestBody to set
	 */
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	/**
	 * @return the requestMethod
	 */
	public String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * @param requestMethod the requestMethod to set
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * @return the requestPath
	 */
	public String getRequestPath() {
		return requestPath;
	}

	/**
	 * @param requestPath the requestPath to set
	 */
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	/**
	 * @return the requestQueries
	 */
	public HashMap<String, String> getRequestQueries() {
		return requestQueries;
	}

	/**
	 * @param requestQueries the requestQueries to set
	 */
	public void setRequestQueries(HashMap<String, String> requestQueries) {
		this.requestQueries = requestQueries;
	}

	/**
	 * @param parser the parser to set
	 */
	public void setParser(RequestLineParser parser) {
		this.parser = parser;
	}






}
