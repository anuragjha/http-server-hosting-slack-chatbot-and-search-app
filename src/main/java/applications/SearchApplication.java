/**
 * 
 */
package applications;

import handlers.FindHandler;
import handlers.ReviewSearchHandler;
import server.TestServer;

/**
 * @author anuragjha
 *
 */


public class SearchApplication {
	public static void main(String[] args) {
		int port = 8080;
		TestServer server = new TestServer(port);
		//The request GET /reviewsearch will be dispatched to the 
		//handle method of the ReviewSearchHandler.
		server.addMapping("/reviewsearch", new ReviewSearchHandler());
		//The request GET /find will be dispatched to the 
		//handle method of the FindHandler.
		server.addMapping("/find", new FindHandler());
		server.startup();
	}
}


