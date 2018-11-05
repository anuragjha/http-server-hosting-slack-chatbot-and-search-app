/**
 * 
 */
package applications;

import java.util.logging.Level;

import cs601.project1.FileValidator;
import cs601.project1.JsonReviewHandler;
import cs601.project3.CmdLineArgsValidator;
import cs601.project3.Project2Init;
import cs601.project3.Project2InitReader;
import cs601.project3.Project2Logger;
import handlers.FindHandler;
import handlers.ReviewSearchHandler;
import server.TestServer1;

/**
 * @author anuragjha
 *  SearcApplication is main class that controls the flow of the program
 */


public class SearchApplication {

	private static Project2Init init;
	private String[] files;
	
	public SearchApplication(String[] files) {
		this.files = files;
		this.buildAmazonSearchApp(this.files);
		this.initializeLogger();
	}

	
	private void initializeLogger() {
		Project2Logger.initialize("Search Application - " /*+ init.getBrokerType(), init.getLoggerFile()*/,"SearchAppLoggerFile.txt");
		for(String file : this.files) {
			Project2Logger.write(Level.INFO, "File to process: "+ file, 0);
		}

	}

	
	public void closeLogger() {
		Project2Logger.close();
	}

	/**
	 * buildAmazonSearchApp method reads review file/s and build index of words
	 * @param files
	 */
	private void buildAmazonSearchApp(String[] files) {

		for(String file : files) {
			System.out.println("Processing File: " + file);
			if(new FileValidator().check(file))	{ 
				new JsonReviewHandler(file);
			} else	{
				System.out.println("Exiting application"); //validation of arguments failed
				this.closeLogger();
				System.exit(1);
			}
		}
		System.out.println("Json files read and DataStores built successfully");
	}


	/**
	 * startApplication method binds the application to the port, where it listens to client request
	 * @param port
	 */
	private void startApplication(int port) {
		
		

		TestServer1 server = new TestServer1(port);
		//The request GET /reviewsearch will be dispatched to the 
		//handle method of the ReviewSearchHandler.
		server.addMapping("/reviewsearch", new ReviewSearchHandler());
		//The request GET /find will be dispatched to the 
		//handle method of the FindHandler.
		server.addMapping("/find", new FindHandler());
		server.startup();
		
		this.closeLogger();
	}


	public static void main(String[] args) {
		
		if(new CmdLineArgsValidator().check(args))	{
			//reading configuration file content into Project2Init object
			init = Project2InitReader.project2InitjsonReader(args[0]);
		}
		else {
			System.out.println("Unable to initialize, exiting system");
			System.exit(1);
		}

		String[] files = {"reviews_Cell_Phones_and_Accessories_5.json"}; //take from config file
		SearchApplication searchApplication = new SearchApplication(files);

		int port = 8080; //config file
		searchApplication.startApplication(port);

	}
}


