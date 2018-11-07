/**
 * 
 */
package cs601.project3;

import java.util.logging.Level;

import handlers.FindHandler;
import handlers.ReviewSearchHandler;
import searchPack.AmazonDataStore;
import searchPack.FileValidator;
import searchPack.JsonQAHandler;
import searchPack.JsonReviewHandler;
import server.TestServer1;

/**
 * @author anuragjha
 *  SearcApplication is main class that controls the flow of the program
 */
public class SearchApplication {

	private static SearchInit searchInit;  //config object


	/**
	 * constructor
	 * @param init
	 */
	public SearchApplication(SearchInit init) {

		searchInit = init;
		//this.files = searchInit.getInputFiles();
		//this.searchInit.getInputFiles()
		System.out.println("Search Config: " + searchInit.toString());
		this.buildAmazonSearchApp(searchInit.getReviewInputFiles(), searchInit.getQaInputFiles());
		this.initializeLogger();

	}

	/**
	 * initializeLogger opens a logger
	 */
	private void initializeLogger() {
		Project3Logger.initialize("SearchLogger", searchInit.getLoggerFile());

		Project3Logger.write(Level.INFO, "Search Application", 0);
		Project3Logger.write(Level.INFO, "Config file content :\n" + searchInit.toString() + "\n", 0);
		if(searchInit.getReviewInputFiles() != null) {
			for(String file : searchInit.getReviewInputFiles()) {
				Project3Logger.write(Level.INFO, "Review File to process:  "+ file, 0);
			}
		}

		if(searchInit.getQaInputFiles() != null) {
			for(String file : searchInit.getQaInputFiles()) {
				Project3Logger.write(Level.INFO, "Review File to process:  "+ file, 0);
			}
		}

	}


	/**
	 * buildAmazonSearchApp method reads review file/s and build index of words
	 * @param files
	 */
	private void buildAmazonSearchApp(String[] reviewFiles, String[] qaFiles) {

		for(String file : reviewFiles) {
			System.out.println("Processing Review File: " + file);

			if(new FileValidator().check(file))	{ 
				new JsonReviewHandler(file);
			} else	{
				System.out.println("Exiting application"); //validation of arguments failed

				Project3Logger.close();
				System.exit(1);
			}
		}


		System.out.println("Sorting Amazon Word Data Store");
		AmazonDataStore.ONE.sortAmazonWordDataStore();

		for(String file : qaFiles) {
			System.out.println("Processing QA File: " + file);

			if(new FileValidator().check(file))	{ 
				new JsonQAHandler(file);
			} else	{
				System.out.println("Exiting application"); //validation of arguments failed

				Project3Logger.close();
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
		//		Project3Logger.write(Level.INFO, "Paths Mapped: \"/reviewsearch\", new ReviewSearchHandler()", 0);
		//The request GET /find will be dispatched to the 
		//handle method of the FindHandler.
		server.addMapping("/find", new FindHandler());
		//		Project3Logger.write(Level.INFO, "Paths Mapped: \"/find\", new FindHandler()\n", 0);
		server.startup();

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

		Project3Logger.close();

	}
}


