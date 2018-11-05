package cs601.project1;

import java.util.Scanner;
/**
 * UserInterface class implements the user Interface for the the application
 * @author anuragjha
 */
public class UserInterface {

	//contains CmdProcessor instance to help with cmd execution
	private CmdProcessor cmdProcessor;

	/**
	 * constructor for UserInterface class
	 */
	public UserInterface()	{
		cmdProcessor = new CmdProcessor();
	}

	/**
	 * startInterface method starts the process for accepting User commands 
	 */
	public void startInterface()	{

		//cmd - exit  to end interface screen
		cmdProcessor.getHelp();
		startScanning();
	}

	/**
	 * startScanning method initialises the scan to accept User input, 
	 * and controls the closing of user interface
	 */
	private void startScanning()	{
		String cmd = "start";
		try(
				Scanner scan = new Scanner(System.in);	
				)	{

			while(!cmd.toLowerCase().equals("exit"))	{

				System.out.print("Enter command line input: ");
				cmd = scan.nextLine();
				String[] cmdList = cmd.split(" ");
				//when cmd is exit
				if(cmd.toLowerCase().equals("exit"))	{
					System.out.println("\n\nApplication Closed");	
					System.exit(0);
				}
				//when cmd is other than exit
				if(cmdProcessor.isCmdValid(cmdList))	{ //cmd is valid
					this.cmdFinder(cmdList);
				}
				else	{
					System.out.println("Input command not valid, see help");
				}

			}
		}
	}



	/**
	 * cmdFinder method invokes processCmd method of CmdProcessor to execute the User cmd
	 * @param cmdList
	 */
	private void cmdFinder(String[] cmdList)	{
		//processing cmd
		//System.out.println("Valid input");
		if(cmdList.length == 2)	{
			cmdProcessor.processCmd(cmdList[0].toLowerCase(), 
					cmdList[1].replaceAll("[^A-Za-z0-9]", "").toLowerCase());
		}					
		else	{
			cmdProcessor.processCmd(cmdList[0].toLowerCase());
		}
	}




	public static void main(String[] args)	{

	//	UserInterface ui = new UserInterface();
	//	ui.startInterface();
	}



}
