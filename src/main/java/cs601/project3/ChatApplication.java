/**
 * 
 */
package cs601.project3;


import java.util.logging.Level;

import handlers.ChatHandler;
import server.TestServer1;

/**
 * @author anuragjha
 * ChatApplication is main class that controls the flow of the program
 *
 */
public class ChatApplication {

	private static ChatInit chatInit;
	private static Project3Logger chatLogger;


	/**
	 * constructor
	 * @param init
	 */
	public ChatApplication(ChatInit init) {
		chatInit = init;
		this.initializeLogger();
	}


	/**
	 * @return the chatInit
	 */
	public static ChatInit getChatInit() {
		return chatInit;
	}


	/**
	 * initializeLogger method opens the logger to be used
	 */
	private void initializeLogger() {
		Project3Logger.initialize("Chat Application - " + chatInit.getPort(), chatInit.getLoggerFile());
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

		Project3Logger.close();

	}

}
