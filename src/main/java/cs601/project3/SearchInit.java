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
	private String[] reviewInputFiles; //contains list of review input files
	private String[] qaInputFiles; //contains list of review input files
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
	 * @return the reviewInputFiles
	 */
	public String[] getReviewInputFiles() {
		return reviewInputFiles;
	}


	/**
	 * @return the qaInputFiles
	 */
	public String[] getQaInputFiles() {
		return qaInputFiles;
	}



	@Override
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("port: " + this.port + "\n");
		sb.append("loggerFile: " + this.loggerFile + "\n");
		sb.append("review input files:\n");
		
		for(String file : reviewInputFiles)	{
			sb.append(file + "\n");
		}
		
		sb.append("qa input files:\n");
		for(String file : qaInputFiles)	{
			sb.append(file + "\n");
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
