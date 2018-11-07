/**
 * 
 */
package handlers;

import httpObjects.HTTPRequest;
import httpObjects.HTTPResponse;

/**
 * @author anuragjha
 * Handlers interface 
 */
public interface Handlers {

	public void handle(HTTPRequest request, HTTPResponse response);


}
