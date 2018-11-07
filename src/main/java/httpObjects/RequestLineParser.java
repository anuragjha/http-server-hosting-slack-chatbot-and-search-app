/**
 * 
 */
package httpObjects;

import java.util.HashMap;

/**
 * @author anuragjha
 * RequestLineParser deals with parsing the request Line and finding queries 
 */
public class RequestLineParser {

	private String[] requestLineParts;
	//private String postRequestQueries;
	private final boolean validRequest;

	/**
	 * constructor
	 * @param requestLine
	 */
	public RequestLineParser(String requestLine) {
		requestLineParts = requestLine.split(" ");
		if(this.isRequestValid()) {
			this.validRequest = true;
		}
		else {
			this.validRequest = false;
		}

	}


	/**
	 * @return the requestLineParts
	 */
	private String[] getRequestLineParts() {
		return requestLineParts;
	}

	/**
	 * isRequestValid method ckecks if the request is valid
	 * @return
	 */
	private boolean isRequestValid()	{
		if((this.requestLineParts.length == 3))	{ //add more checks
			if((this.checkIfHttpVersion1() || (this.checkIfHttpVersion0()))) {

				return true;
			}
		}
		return false;
	}


	/**
	 * @return the getRequestLineMethod
	 */
	public String getRequestLineMethod() {
		if(this.validRequest) {
			return requestLineParts[0];
		}
		return "";
	}


	/**
	 * @return the getRequestLinePath
	 */
	public String getRequestLinePath() {
		String path = "";
		if(this.validRequest) {
			path = requestLineParts[1].split("\\?")[0];
		}
		return path;
	}

	/**
	 * @return the getRequestLinePath
	 */
	public String getRequestLineHTTPVersion() {
		if(this.validRequest) {
			return requestLineParts[2];
		}
		return "";
	}


	/**
	 * checkIfGET method checks if the request method is GET
	 * @return
	 */
	public boolean checkIfGET()	{
		if(this.validRequest) {
			return this.requestLineParts[0].equals(HttpConstants.GET);
		}
		return false;
	}

	/**
	 * checkIfPOST method checks if the request method is POST
	 * @return
	 */
	public boolean checkIfPOST()	{
		if(this.validRequest) {
			return this.requestLineParts[0].equals(HttpConstants.POST);
		}
		return false;
	}

	/**
	 * checkIfHttpVersion1 checks if the request is HTTP 1.1
	 * @return
	 */
	public boolean checkIfHttpVersion1()	{
		if((this.requestLineParts.length == 3)) {
			if(this.requestLineParts[2].trim().equals("HTTP/1.1"))	{
				return true;
			}	
		}
		return false;
	}


	/**
	 * checkIfHttpVersion1 checks if the request is HTTP 1.0
	 * @return
	 */
	public boolean checkIfHttpVersion0()	{
		if((this.requestLineParts.length == 3)) {
			if(this.requestLineParts[2].matches("HTTP/1.0"))	{
				return true;
			}	
		}
		return false;
	}


	/**
	 * getRequestLineQueries method gives the key value pairs of queries in request line
	 * @return queries HashMap or Null
	 */
	public HashMap<String, String> getRequestLineQueries()	{
		if(this.validRequest && this.requestLineParts[1].contains("?")) {
			HashMap<String, String> queries = new HashMap<String, String>();
			String diffQueries = requestLineParts[1].split("\\?")[1];
			String[] keyValuePairs = diffQueries.split("&");
			for(String keyValuePair : keyValuePairs) {
				if(queries.containsKey(keyValuePair.split("=")[0])) { //duplicate key names
					return null;
				}
				queries.put(keyValuePair.split("=")[0], keyValuePair.split("=")[1]);
			}
			return queries;
		}
		return null;

	}

	/**
	 * getRequestLineQueries method gives the key value pairs of query in request body
	 * @return queries HashMap or Null
	 */
	public HashMap<String, String> getRequestBodyQueries(String requestBody)	{
		if(isRequestValid() && requestBody != null) {
			HashMap<String, String> queries = new HashMap<String, String>();
			//String diffQueries = requestBody.split("\\?")[1];
			String[] keyValuePairs = requestBody.split("&");
			for(String keyValuePair : keyValuePairs) {
				if(queries.containsKey(keyValuePair.split("=")[0])) { //duplicate key names
					return null;
				}
				if(keyValuePair.split("=").length == 2) {
					queries.put(keyValuePair.split("=")[0], keyValuePair.split("=")[1]);
				}

			}
			return queries;
		}
		return null;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
