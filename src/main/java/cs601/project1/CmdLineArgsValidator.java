/**
 * 
 */
package cs601.project1;

import java.io.File;

/**
 * @author anuragjha
 * CmdLineArgsValidator class checks if the cmd line argument is valid
 */
public class CmdLineArgsValidator {

	/**
	 * check method takes in the array of arguments and processes the args to confirm validity
	 * @param args
	 * @return true/false
	 */
	public boolean check(String[] args)	{
		return validateCmd(args);
	}

	/**
	 * validateCmd method implements the args check to determine if the argument is valid
	 * @param args
	 * @return
	 */
	private boolean validateCmd(String[] args)	{
		if(checkArgLength(args))	{
			if(checkArgIfCorrect(args))	{
				if(checkIfFileExists(args))	{
					return true;
				}
				else	{
					System.out.println("Cannot find either one or both the file.");
					return false;
				}
			}
			else	{
				System.out.println("Please input line params in specified format");
				System.out.println("-reviews <review_file_name.json> -qa <qa_file_name.json>");
				return false;
			}
		}
		else	{
			System.out.println("Please input 4 cmd line params: "
					+ "-reviews <review_file_name.json> -qa <qa_file_name.json>");
			return false;
		}
	}


	/**
	 * checkArgLength method checks for the number of arguments
	 * @param args
	 * @return
	 */
	private boolean checkArgLength(String[] args)	{
		if(args.length == 2)	{
			return true;
		}
		return false;
	}

	/**
	 * checkArgIfCorrect method checks all the arguments are valid
	 * @param args
	 * @return
	 */
	private boolean checkArgIfCorrect(String[] args)	{
		if( (args[0].equals("-reviews")) && (args[1].endsWith(".json")) ){

			return true;
		}
		return false;
	}

	/**
	 * checkIfFileExists method checks if the files are present in the specified path
	 * @param args
	 * @return
	 */
	private boolean checkIfFileExists(String[] args)	{
		if((new File(args[1]).exists()))	{
			return true;
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
