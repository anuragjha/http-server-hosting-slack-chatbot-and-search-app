/**
 * 
 */
package cs601.project3;

/**
 * @author anuragjha
 * Project2Init class holds the config information 
 *
 */
public class ChatInit {

	private int port;
	private String loggerFile;
	
	/**
	 * constructor
	 */
	public ChatInit()	{
		
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


	@Override
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("port: " + this.port + "\n");
		sb.append("loggerFile: " + this.loggerFile + "\n");
	
		return sb.toString();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
