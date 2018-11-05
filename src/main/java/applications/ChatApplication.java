/**
 * 
 */
package applications;


import java.util.logging.Level;

import cs601.project3.Project2Init;
import cs601.project3.Project2Logger;
import handlers.ChatHandler;
import server.TestServer1;

/**
 * @author anuragjha
 * ChatApplication is main class that controls the flow of the program
 *
 */
public class ChatApplication {
	
	private static Project2Init init;
	
	public ChatApplication() {
		this.initializeLogger();
	}
	
	private void initializeLogger() {
		Project2Logger.initialize("Chat Application - " /*+ init.getBrokerType(), init.getLoggerFile()*/,"chatAppLoggerFile.txt");

	}
	
	public void closeLogger() {
		Project2Logger.close();
	}
	
	
	/**
	 * startApplication method binds the application to the port, where it listens to client request
	 * @param port
	 */
	private void startApplication(int port) {
		TestServer1 server = new TestServer1(port);
		server.addMapping("/slackbot", new ChatHandler());
		server.startup();
	}
	
	
	
	public static void main(String[] args) {
		
		ChatApplication chatApplication = new ChatApplication();
		
		int port = 9090; //config 
		chatApplication.startApplication(port);
	
	}
	
}
