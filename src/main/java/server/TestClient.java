package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestClient {
	
	private static int id = 0;
	private final int PORT = 5000;
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

			String userline = reader.nextLine();
			System.out.println("client msg : " + userline);
			while(userline != null && !userline.equals("bye"))	{
				out.println(userline);
				System.out.println("Ack userLine: " + in.readLine());
				userline = reader.nextLine();
			}
			out.println("client last message");
			out.println("exit");
		}
	}

	public static void main(String[] args)	{
		TestClient tc = new TestClient();
		TestClient tc2 = new TestClient();
	}
}
