/**
 * 
 */
package applications;

import java.util.logging.Level;

import cs601.project1.FileValidator;
import cs601.project1.JsonReviewHandler;
import cs601.project3.CmdLineArgsValidator;
import cs601.project3.InitJsonReader;
import cs601.project3.Project2Logger;
import cs601.project3.SearchInit;
import handlers.FindHandler;
import handlers.ReviewSearchHandler;
import server.TestServer1;

/**
 * @author anuragjha
 *  SearcApplication is main class that controls the flow of the program
 */


public class SearchApplication {

	private static SearchInit searchInit;
	//private String[] files;
	
	public SearchApplication(SearchInit init) {
		searchInit = init;
		//this.files = searchInit.getInputFiles();
		//this.searchInit.getInputFiles()
		this.buildAmazonSearchApp(searchInit.getInputFiles());
		this.initializeLogger();
	}

	
	private void initializeLogger() {
		Project2Logger.initialize("Search Application - at port: " + searchInit.getPort(), searchInit.getLoggerFile());
		for(String file : searchInit.getInputFiles()) {
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
	private void startApplication() {
		
		

		TestServer1 server = new TestServer1(searchInit.getPort());
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
		
		SearchInit init;
		
		if(new CmdLineArgsValidator().check(args))	{
			//reading configuration file content into SearchInit object
			init = (SearchInit) InitJsonReader.project3InitJsonReader(args[0], SearchInit.class);
		}
		else {
			init = null;
			System.out.println("Unable to initialize, exiting system");
			System.exit(1);
		}

		SearchApplication searchApplication = new SearchApplication(init);
		
		searchApplication.startApplication();

	}
}


