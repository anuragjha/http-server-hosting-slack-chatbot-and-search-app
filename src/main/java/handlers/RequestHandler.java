package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import protocolParser.RequestLineParser;


public class RequestHandler implements Handlers,Runnable {

	private Socket socket = null;

	public RequestHandler(Socket clientSocket)	{
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
	 	
		System.out.println("working on Client request");
		try(
				BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter outStream = new PrintWriter(socket.getOutputStream(), true);
				)	{
			
			//reads the request line - method, query string and http protocol version
			String requestLine = inStream.readLine();
			//checking client request
			//ToDo : create a class to utilise RequestLineParser
			
			//reads the rest of the header
			String requestHeader = "";
			String line = inStream.readLine();				
			while(line != null && !line.trim().isEmpty()) {
				requestHeader += line + "\n";
				line = inStream.readLine();
			}
			
			System.out.println("Request Line: " + requestLine);
			System.out.println("Headers: \n" + requestHeader);
			
			//reading first line of body for post method /// getting parameters for post method
			if(new RequestLineParser(requestLine).checkIfPOST())	{
				//for(int i = 0; i<=0; i++)	{
				String requestBodyTopLine = inStream.readLine();
				System.out.println("requestBodyTopLine: \n" + requestBodyTopLine);
			//	}
			}

			/// To start from processing request line ///// !!!!!!!!!!!!!!!!!
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//Binary Server to get body of text//////// !!!!!!!!!!!!!!!!!!!!!!!
			
			
//			String message = "";
//			String line = inStream.readLine();
//
//			while(line != null && !line.trim().isEmpty()) {
//				message += line + "\n";
//				line = inStream.readLine();
//			}
//			System.out.println("Request: \n" + message);

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

