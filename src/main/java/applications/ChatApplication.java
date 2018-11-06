/**
 * 
 */
package applications;


import cs601.project3.ChatInit;
import cs601.project3.CmdLineArgsValidator;
import cs601.project3.InitJsonReader;
import cs601.project3.Project2Logger;
import cs601.project3.SearchInit;
import handlers.ChatHandler;
import server.TestServer1;

/**
 * @author anuragjha
 * ChatApplication is main class that controls the flow of the program
 *
 */
public class ChatApplication {
	
	private static ChatInit chatInit;
	
	public ChatApplication(ChatInit init) {
		chatInit = init;
		this.initializeLogger();
	}
	
	private void initializeLogger() {
		Project2Logger.initialize("Chat Application - " + chatInit.getPort(), chatInit.getLoggerFile());
	}
	
	public void closeLogger() {
		Project2Logger.close();
	}
	
	
	/**
	 * startApplication method binds the application to the port, where it listens to client request
	 * @param port
	 */
	private void startApplication() {
		TestServer1 server = new TestServer1(chatInit.getPort());
		server.addMapping("/slackbot", new ChatHandler());
		server.startup();
	}
	
	
	
	public static void main(String[] args) {
		
		ChatInit init;
		
		if(new CmdLineArgsValidator().check(args))	{
			//reading configuration file content into ChatInit object
			init = (ChatInit) InitJsonReader.project3InitJsonReader(args[0], ChatInit.class);
		}
		else {
			init = null;
			System.out.println("Unable to initialize, exiting system");
			System.exit(1);
		}
		
		ChatApplication chatApplication = new ChatApplication(init);
		
		chatApplication.startApplication();
	
	}
	
}
