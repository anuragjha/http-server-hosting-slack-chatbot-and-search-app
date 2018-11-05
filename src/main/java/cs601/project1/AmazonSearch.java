package cs601.project1;

/**
 * AmazonSearch class is main class that controls the flow of the program
 * @author anuragjha
 *
 */
public class AmazonSearch {
	/**
	 * main method starts the program, it processes the json files 
	 * and then calls the UserInterface to start scanning
	 * @param args 
	 * 			-reviews <review_file_name> -qa <qa_file_name>
	 */
	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		System.out.println("Starting Amazon Reviews \n"); // Start of control flow
		//validates arguments and returns true is everything is correct
		if(new CmdLineArgsValidator().check(args))	{ 
			//read the Json files
			///fastest with current design of reading each record/line of file
//			new JsonReviewHandler(args[1]); 
			new JsonReviewHandler(args[1]); 
			//new JsonQAHandler(args[3]);

		}else	{
			//validation of arguments failed
			System.out.println("Exiting application");
			System.exit(0);
		}

		System.out.println("Json files read and DataStores built successfully");
		System.out.println("time taken :"+ ((System.currentTimeMillis() - startTime)/1000) + " seconds");
		//System.out.println(AmazonDataStore.ONE.reviewWordDataStore.getIndex().values().entrySet());

		//Instantiate User Interface and start scanning
		UserInterface ui = new UserInterface();
		ui.startInterface();

	}


}