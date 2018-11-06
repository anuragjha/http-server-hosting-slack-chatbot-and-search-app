/**
 * 
 */
package cs601.project3;

/**
 * @author anuragjha
 * Project2Init class holds the config information 
 *
 */
public class SearchInit {

	private int port;
	private String loggerFile;
	private String[] inputFiles; //contains list of input files
	/**
	 * constructor
	 */
	public SearchInit()	{
		
	}
	
	
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}



	/**
	 * @return the loggerFile
	 */
	public String getLoggerFile() {
		return loggerFile;
	}



	/**
	 * @return the inputFiles
	 */
	public String[] getInputFiles() {
		return inputFiles;
	}



	@Override
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("port: " + this.port + "\n");
		sb.append("loggerFile: " + this.loggerFile + "\n");
		for(String file : inputFiles)	{
			sb.append("inputFile: " + file + "\n");
		}
		
		return sb.toString();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
