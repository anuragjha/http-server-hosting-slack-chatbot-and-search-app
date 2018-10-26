/**
 * 
 */
package applications;

import handlers.ChatHandler;
import server.TestServer;

/**
 * @author anuragjha
 *
 */
public class ChatApplication {

	public static void main(String[] args) {
		int port = 8080;
		TestServer server = new TestServer(port);
		server.addMapping("/slackbot", new ChatHandler());
		server.startup();
	}
	
}
