package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestClient {
	
	private volatile static int id = 0;
	private final int PORT = 9090;
	private final String ip = "127.0.0.1";

	public TestClient()	{
		id += 1;
		System.out.println("ClientID: " + id);
		this.manageClient();
	}

	public void manageClient()	{

		try {
			this.runClient();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void runClient() throws UnknownHostException, IOException	{
		try (
				Socket client = new Socket(this.ip,  this.PORT);
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				Scanner reader = new Scanner(System.in)
				){

			//get request
			String userline = reader.nextLine();
			//String userline = "http://127.0.0.1/reviewSearch?query=mountFuji";
			System.out.println("client msg : " + userline);
			while(/*userline != null &&*/ !userline.equals("bye"))	{
				out.println(userline);
				//receive response
				System.out.println("Ack userLine: " + in.readLine());
				//get request
	 			userline = reader.nextLine();
			}
			out.println("client last message");
			reader.close();
			in.close();
			out.close();
			client.close();
		}
	}
	
	
	public void clientClose()	{
		
	}
	/**
	 * getRequest method creates a http request and returns the request string
	 * @param host
	 * @param path
	 * @return
	 */
	private static String getRequest(String host, String path) {
		String request = "GET " + path + " HTTP/1.1" + "\n" //GET request
				+ "Host: " + host + "\n" //Host header required for HTTP/1.1
				+ "Connection: keep-alive\n" //make sure the server closes the connection after we fetch one page
				+ "\r\n";								
		return request;
	}
	

	public static void main(String[] args)	{
		TestClient tc = new TestClient();
	
	}
}
