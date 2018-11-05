/**
 * 
 */
package handlers;

import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;

/**
 * @author anuragjha
 *
 */
public interface Handlers {

	public void handle(HTTPRequest request, HTTPResponse response);
	

}
