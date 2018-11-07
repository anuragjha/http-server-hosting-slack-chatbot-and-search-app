package slackPack;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cs601.project3.ChatApplication;
import cs601.project3.Project3Logger;
import searchPack.AmazonReviews;

/**
 * SlackClient class sends message and reads response from slack
 * @author anuragjha
 *
 */
public class SlackClient {

	private SlackMessage slackMessage;
	private boolean isOK;

	/*
	 * constructor
	 */
	public SlackClient(String text)	{
		slackMessage = new SlackMessage(ChatApplication.getChatInit().getChannel(), text); // message body
		isOK = false;
	}


	/**
	 * @return the isOK
	 */
	public boolean isOK() {
		return isOK;
	}

	/**
	 * sendMessage method sends message to Slack
	 */
	public void sendMessage() {
		try {
			this.doSendMessage();
		} catch (IOException ioe) {
			System.out.println("Unable to send message");
		}
	}

	/**
	 * doSendMessage method implements sending messages to and reading response from Slack
	 * @throws IOException
	 */
	private void doSendMessage() throws IOException {



		String jsonMessage = new Gson().toJson(slackMessage);
		System.out.println(jsonMessage);

		byte[] postData = jsonMessage.getBytes("UTF-8");
		//create URL object
		URL url = new URL("https://slack.com/api/chat.postMessage");

		//create secure connection 
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

		//set HTTP method
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		connection.setRequestProperty("Authorization", "Bearer xoxp-378520430422-387093128722-470265812229-bd58615896c703c3c4ca1284ca204b70");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Content-Length", Integer.toString(postData.length));

		connection.setDoOutput(true);


		connection.connect();

		try(DataOutputStream out = new DataOutputStream(connection.getOutputStream())){
			out.write(postData);
		}

		System.out.println("HEADERS");
		printHeaders(connection);

		System.out.println("BODY");
		printBody(connection);
		Project3Logger.write(Level.INFO, "Response Body : \n", 0);


	}


	/**
	 * printHeaders method reads Slack response headers
	 * @param connection
	 */
	private void printHeaders(URLConnection connection) {
		Map<String,List<String>> headers = connection.getHeaderFields();
		for(String key: headers.keySet()) {
			System.out.print("KEY: " + key);
			List<String> values = headers.get(key);
			System.out.print("\tVALUES: ");
			for(String value: values) {
				System.out.println("\t" + value);
			}
		}		
	}

	/**
	 * printBody method reads Slack response body
	 * @param connection
	 * @throws IOException
	 */
	private void printBody(URLConnection connection) throws IOException {
		//HashMap<String,String> slackResponse = new HashMap<String,String>();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String line;
		while((line = reader.readLine()) != null) {
			Project3Logger.write(Level.INFO, line + "\n", 0);
			try {
				//parses each line into JsonObject
				JsonObject object =  new JsonParser().parse(line).getAsJsonObject();
				//creates SlackResponse object from the Json Object
				SlackResponse slackResponse = new Gson().fromJson(object, SlackResponse.class);
				isOK = slackResponse.getOk();  // setting isOK to true or false
			} catch(JsonSyntaxException jse)	{
				System.out.println("Skipping line ...");
			}
			System.out.println(line);
		}
		System.out.println("slack response : " + isOK);

	}



	public static void main(String[] args) {
	}
}
