/**
 * 
 */
package protocolParser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author anuragjha
 *
 */
public class RequestLineParser {

	private String[] requestLineParts;
	private String postRequestQueries;


	public RequestLineParser(String requestLine)	{
		requestLineParts = requestLine.split("\\s+");
		postRequestQueries = null;
	}
	
	public RequestLineParser(String requestLine, String postMethodQueries)	{
		requestLineParts = requestLine.split("\\s+");
		postRequestQueries = postMethodQueries;
	}


	/**
	 * @return the requestLineParts
	 */
	private String[] getRequestLineParts() {
		return requestLineParts;
	}

	private boolean isRequestValid()	{
		if(this.requestLineParts.length == 3)	{
			return true;
		}
		return false;
	}


	/**
	 * @return the getRequestLineMethod
	 */
	private String getRequestLineMethod() {
		if(isRequestValid())	{
			return requestLineParts[0];
		}
		return "";
	}


	/**
	 * @return the getRequestLinePath
	 */
	private String getRequestLinePath() {
		String path = "";
		if(isRequestValid())	{
			path = requestLineParts[1].split("\\?")[0];
		}
		return path;
	}

	/**
	 * @return the getRequestLinePath
	 */
	private String getRequestLineHTTPVersion() {
		if(isRequestValid())	{
			return requestLineParts[2];
		}
		return "";
	}



	public boolean checkIfGET()	{
		if(isRequestValid())	{
			return this.requestLineParts[0].equals("GET");
		}
		return false;
	}

	public boolean checkIfPOST()	{
		if(isRequestValid())	{
			return this.requestLineParts[0].equals("POST");
		}
		return false;
	}

	private boolean checkIfHttpVersion1()	{
		if(isRequestValid())	{
			if(this.requestLineParts[2].matches("HTTP/1.1"))	{
				return true;
			}	
		}
		return false;
	}

	private boolean checkIfHttpVersion0()	{
		if(isRequestValid())	{
			if(this.requestLineParts[2].matches("HTTP/1.0"))	{
				return true;
			}	
		}
		return false;
	}


	/**
	 * @return queries HashMap or Null
	 */
	private HashMap<String, String> getRequestLineQueries()	{
		if(isRequestValid() && this.requestLineParts[1].contains("?"))	{
			HashMap<String, String> queries = new HashMap<String, String>();
			String diffQueries = requestLineParts[1].split("\\?")[1];
			String[] keyValuePairs = diffQueries.split("&");
			for(String keyValuePair : keyValuePairs)	{
				queries.put(keyValuePair.split("=")[0], keyValuePair.split("=")[1]);
			}
			return queries;
		}
		return null;

	}
	
	/**
	 * @return queries HashMap or Null
	 */
	private HashMap<String, String> getPostRequestQueries()	{
		if(isRequestValid() && this.postRequestQueries != null)	{
			HashMap<String, String> queries = new HashMap<String, String>();
			String diffQueries = this.postRequestQueries.split("\\?")[1];
			String[] keyValuePairs = diffQueries.split("&");
			for(String keyValuePair : keyValuePairs)	{
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
		for(String part : rp.requestLineParts)	{
			System.out.println("Request parts: " + part);
		}
		System.out.println("\nrequest valid?: " + rp.isRequestValid());
		System.out.println("request method: " + rp.getRequestLineMethod());
		System.out.println("request path: " + rp.getRequestLinePath());
		System.out.println("request querys: ");
		HashMap<String, String> kV = new HashMap<String,String>(rp.getRequestLineQueries());
		for(String key : kV.keySet())	{
			System.out.println("key: " + key + "\tvalue: " + kV.get(key));
		}
		System.out.println("request HTTP: " + rp.getRequestLineHTTPVersion() + "\n");
		System.out.println(rp.checkIfGET());
		System.out.println(rp.checkIfPOST());
		System.out.println(rp.checkIfHttpVersion0());
		System.out.println(rp.checkIfHttpVersion1());

	}

}
