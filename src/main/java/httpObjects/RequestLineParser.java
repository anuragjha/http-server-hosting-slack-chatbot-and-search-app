/**
 * 
 */
package httpObjects;

import java.util.HashMap;
import java.util.Map;

import htmlGenerator.HttpConstants;

/**
 * @author anuragjha
 *
 */
public class RequestLineParser {

	private String[] requestLineParts;
	//private String postRequestQueries;
	private final boolean validRequest;


	public RequestLineParser(String requestLine) {
		requestLineParts = requestLine.split(" ");
		if(this.isRequestValid()) {
			this.validRequest = true;
		}
		else {
			this.validRequest = false;
		}
		//postRequestQueries = null;
	}

	//	public RequestLineParser(String requestLine, String postMethodQueries) {
	//		requestLineParts = requestLine.split("\\s+");
	//postRequestQueries = postMethodQueries;
	//	}


	/**
	 * @return the requestLineParts
	 */
	private String[] getRequestLineParts() {
		return requestLineParts;
	}


	private boolean isRequestValid()	{
		System.out.println("http version:::::: " + requestLineParts[2]);
		if((this.requestLineParts.length == 3))	{ //add more hecks
			//if(this.checkIfHttpVersion1()) {
			
				return true;
			//}
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
	private String getRequestLineHTTPVersion() {
		if(this.validRequest) {
			return requestLineParts[2];
		}
		return "";
	}



	public boolean checkIfGET()	{
		if(this.validRequest) {
			return this.requestLineParts[0].equals(HttpConstants.GET);
		}
		return false;
	}


	public boolean checkIfPOST()	{
		if(this.validRequest) {
			return this.requestLineParts[0].equals(HttpConstants.POST);
		}
		return false;
	}

	private boolean checkIfHttpVersion1()	{
		if(this.validRequest) {
			if(this.requestLineParts[2].matches("HTTP/1.1"))	{
				return true;
			}	
		}
		return false;
	}

	private boolean checkIfHttpVersion0()	{
		if(this.validRequest) {
			if(this.requestLineParts[2].matches("HTTP/1.0"))	{
				return true;
			}	
		}
		return false;
	}


	/**
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
				queries.put(keyValuePair.split("=")[0], keyValuePair.split("=")[1]);
			}
			return queries;
		}
		return null;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//RequestParser rp = new RequestParser("GET / HTTP/1.1");
		RequestLineParser rp = new RequestLineParser("GET /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		for(String part : rp.requestLineParts) {
			System.out.println("Request parts: " + part);
		}
		System.out.println("\nrequest valid?: " + rp.isRequestValid());
		System.out.println("request method: " + rp.getRequestLineMethod());
		System.out.println("request path: " + rp.getRequestLinePath());
		System.out.println("request querys: ");
		HashMap<String, String> kV = new HashMap<String,String>(rp.getRequestLineQueries());
		for(String key : kV.keySet()) {
			System.out.println("key: " + key + "\tvalue: " + kV.get(key));
		}
		System.out.println("request HTTP: " + rp.getRequestLineHTTPVersion() + "\n");
		System.out.println(rp.checkIfGET());
		System.out.println(rp.checkIfPOST());
		System.out.println(rp.checkIfHttpVersion0());
		System.out.println(rp.checkIfHttpVersion1());

	}

}
