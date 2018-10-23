package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {

	Socket socket = null;
	public ClientThread(Socket clientSocket)	{
		socket = clientSocket;
	}
	public void run()	{
		System.out.println("client accepted");

		try(
				BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);
				)	{
			//String message = "";
			String line = inStream.readLine();
			while(line != null && line!="bye") {
				//message = line;
				System.out.println("Client says : " + line);
				outStream.println("Server says received your message: "+ line);
				//line = inStream.readLine();
				if((line = inStream.readLine()).equals(null))	{
					System.out.println("break");
					break;
				}

			}
			System.out.println("Closing msg says : " + line);
			socket.close();
		} catch (IOException e) {

			e.printStackTrace();
		}


	}

}
