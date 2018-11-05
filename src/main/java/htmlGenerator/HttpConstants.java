/**
 * 
 */
package htmlGenerator;

import java.util.EnumMap;

/**
 * @author anuragjha
 * 
 */
public class HttpConstants {

	public static final String GET = "GET";
	public static final String POST = "POST";
	
	public static final String HTTP0 = "HTTP/1.0";
	public static final String HTTP1 = "HTTP/1.1";
	
	public static final String OK = "HTTP/1.0 200 OK\n" + "\r\n";
	public static final String PAGENOTFOUND = "HTTP/1.0 404 Not Found\n" + "\r\n";
	public static final String METHODNOTFOUND = "HTTP/1.0 405 Method Not Allowed\n" + "\r\n";


}