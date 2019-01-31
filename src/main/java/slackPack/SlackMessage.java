/**
 * 
 */
package slackPack;

/**
 * @author anuragjha
 * SlackMessage class stores the channel and the message for Slack
 */
public class SlackMessage {
	//String token 
	String channel; //
	String text; // = "I hope the tour went well";
	
	
	public SlackMessage(String channel, String text) {
		this.channel = channel;
		this.text = text;
	}
	
	public int getTextLength()	{
		return text.length();
	}

}
