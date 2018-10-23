package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestServer {

	public TestServer(int port)	{
		this.manageServer(port);
	}

	public void manageServer(int port)	{
		try {
			this.runServer(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runServer(int port) throws IOException	{

		System.out.println("starting server");
		ServerSocket server = new ServerSocket(port);
		
		//BufferedReader inStream = null ;
		//PrintWriter outStream = null;
		
		ExecutorService executer = Executors.newFixedThreadPool(5);
		String line= "";
		while(true && !line.equals("exit"))	{
			Socket clientSocket = server.accept();
			Runnable clientThread = new ClientThread(clientSocket);
			executer.execute(clientThread);
			/**
			System.out.println("client accepted");

			inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outStream = new PrintWriter(clientSocket.getOutputStream(), true);

			//String message = "";
			line = inStream.readLine();
			while(line != null) {
				//message = line;
				System.out.println("Client says : " + line);
				outStream.println("Server says received your message: "+ line);
				//line = inStream.readLine();
				if((line = inStream.readLine()).equals(null))	{
					System.out.println("break");
					break;
				}

			}
			System.out.println("Client says : " + line);
			clientSocket.close();
			inStream.close();
			outStream.close();
			*/
			

		}
		server.close();
		


	}



	public static void main(String[] args)	{
		TestServer ts = new TestServer(5000);
	}

}
