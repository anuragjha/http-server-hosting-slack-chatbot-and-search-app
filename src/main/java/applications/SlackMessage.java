/**
 * 
 */
package applications;

/**
 * @author anuragjha
 *
 */
public class SlackMessage {
	//String token = "Bearer xoxp-378520430422-387093128722-470265812229-bd58615896c703c3c4ca1284ca204b70";
	String channel; // = "#project3anurag";
	String text; // = "I hope the tour went well";
	
	
	public SlackMessage(String channel, String text) {
		this.channel = channel;
		this.text = text;
	}
	
	public int getTextLength()	{
		return text.length();
	}

}
