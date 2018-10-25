package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ClientThread implements Runnable {

	Socket socket = null;
	public ClientThread(Socket clientSocket)	{
		socket = clientSocket;
	}
	
	
	private LinkedList<String> FileRead(String inputFile)	{
		
		Path path = Paths.get(inputFile);
		 LinkedList<String> lines = new LinkedList<String>();
		
		try(	
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				)	
		{
			String line;
			while( (line = reader.readLine()) != null )	{
				//send line to patternMatcher method
				lines.add(line);
			}
			
		}
		catch(IOException ioe)	{
			System.out.println(ioe.getMessage());
		}	
	
		return lines;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void run()	{
		System.out.println("client accepted");

		try(
				BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);
				)	{

			String message = "";
			String line = inStream.readLine();

			while(line != null && !line.trim().isEmpty()) {
				message += line + "\n";
				line = inStream.readLine();
			}
			System.out.println("Request: \n" + message);

			String headers = "HTTP/1.0 200 OK\n" +
					"\r\n";

			//String page = "<html> " + 
			//		"<head><title>TEST</title></head>" + 
			//		"<body>This is a Anurag test page.</body>" + 
			//		"</html>";
			LinkedList<String> fileLines = this.FileRead("search.html");
			String outputMsg = "";
			for( String fileLine : fileLines) {
				outputMsg += fileLine + "\n";
			}
			outStream.write(headers);
			//outStream.write(page);
			outStream.write(outputMsg);
			outStream.flush();
			
			



		}catch(IOException ioe)	{
			ioe.printStackTrace();
		}

	}
}
