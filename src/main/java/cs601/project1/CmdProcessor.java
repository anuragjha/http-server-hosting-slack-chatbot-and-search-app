/**
 * 
 */
package cs601.project1;

/**
 * @author anuragjha
 * CmdProcessor class contains the execution logic for the User commands
 */
public class CmdProcessor {


	/**
	 * method starts to check if the user inputs are valid
	 * @param cmdList
	 * @return
	 */
	public boolean isCmdValid(String[] cmdList)	{
		return cmdValidityCheck(cmdList);
	}

	/**
	 * cmdValidityCheck method implements the checking for the user commands
	 * @param cmdList
	 * @return true/false
	 */
	private boolean cmdValidityCheck(String[] cmdList)	{

		String[] validCmds = {"find", "reviewsearch", "qasearch", "reviewpartialsearch", "qapartialsearch"};
		//String cmdList[] = cmd.split(" ");
		if(cmdList.length == 1)	{
			if(cmdList[0].toLowerCase().equals("help"))	{
				return true;
			}
		}
		if(cmdList.length == 2)	{
			//System.out.println("first loop");
			for(String validCmd : validCmds)	{
				if(validCmd.matches(cmdList[0].toLowerCase()))	{
					//	System.out.println("second loop");
					//	if(cmdList[1].replaceAll("[^A-Za-z0-9]", "").toLowerCase().matches("[a-zA-Z0-9]+"))	{
					//		System.out.println("third loop");
					return true;
					//	}
				}
			}
		}
		return false;
	}

	/**
	 * processCmd method takes in 2 parameters to execute the user command
	 * @param cmdMethod
	 * @param cmdTerm
	 */
	public void processCmd(String cmdMethod, String cmdTerm)	{

		switch(cmdMethod)	{
		case "find"	: 
			AmazonDataStore.ONE.getSearcher().getAsinFind(cmdTerm);

			break;
		case "reviewsearch" : 
			AmazonDataStore.ONE.getSearcher().getReviewSearch(cmdTerm);

			break;
		//case "qasearch"	: 
		//	AmazonDataStore.ONE.getSearcher().getQASearch(cmdTerm);

		//	break;
	//	case "reviewpartialsearch" : 
	//		AmazonDataStore.ONE.getSearcher().getReviewPartialSearch(cmdTerm);

	//		break;
	//	case "qapartialsearch" : 
	//		AmazonDataStore.ONE.getSearcher().getQAPartialSearch(cmdTerm);

	//		break;
		}

	}

	/**
	 * processCmd method takes in 1 parameter to execute the user command
	 * @param cmdMethod
	 */
	public void processCmd(String cmdMethod)	{
		switch(cmdMethod)	{
		case "help"	: System.out.println(help());
		break;
		}
	}


	/**
	 * getHelp method is public method that calls help method
	 */
	public void getHelp()	{
		System.out.println(this.help());
	}

	/**
	 * help method displays list of commands the program accepts
	 * @return
	 */
	private String help()	{
		return    "____________________________\n" 
				+ "**** Valid Commands are ****\n"
				+ "1. find <asin>\n"
				+ "2. reviewsearch <term>\n"
				+ "3. qasearch <term>\n"
				+ "4. reviewpartialsearch <term>\n"
				+ "5. qapartialsearch <term>\n"
				+ "6. exit\n"
				+ "____________________________";
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
