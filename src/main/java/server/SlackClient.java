package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

import applications.SlackMessage;

public class SlackClient {
	
	SlackMessage slackMessage;
	
	public SlackClient(String text)	{
		slackMessage = new SlackMessage("#project3manali", text); // message body
	}
	
	public void sendMessage() {
		try {
			this.doSendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//propagate exception to simplify demonstration code
	private void doSendMessage() throws Exception {

		
		
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

//		connection.setRequestProperty("Content-Length", String.valueOf(slackMessage.getTextLength()));
		
		connection.setDoOutput(true);
		
		
		connection.connect();
		
		try(DataOutputStream out = new DataOutputStream(connection.getOutputStream())){
			out.write(postData);
		}

		System.out.println("HEADERS");
		printHeaders(connection);
		System.out.println("BODY");
		printBody(connection);

	}

	public static void printHeaders(URLConnection connection) {
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

	public static void printBody(URLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String line;
		while((line = reader.readLine()) != null) {
			System.out.println(line);
		} 
	}
}
