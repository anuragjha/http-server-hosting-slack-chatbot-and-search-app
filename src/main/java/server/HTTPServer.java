package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is a very poorly designed HTTP server able to reply to one request with a static HTML page.
 * @author srollins
 *
 */
public class HTTPServer {

	public HTTPServer(int port)	{
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
		try(
				ServerSocket server = new ServerSocket(port)	
				){
			ExecutorService executer = Executors.newFixedThreadPool(5);
			String line= "";
			while(true && !line.equals("exit"))	{
				Socket clientSocket = server.accept();
				Runnable clientThread = new ClientThread(clientSocket);
				executer.execute(clientThread);
			}
			server.close();
		}

	}



	public static void main(String[] args) {
		
		//TestServer ts = new TestServer(5000);

		try (ServerSocket serve = new ServerSocket(1024);
				Socket sock = serve.accept();
				BufferedReader instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				PrintWriter writer = new PrintWriter(sock.getOutputStream())) {

			String message = "";
			String line = instream.readLine();

			while(line != null && !line.trim().isEmpty()) {
				message += line + "\n";
				line = instream.readLine();
			}
			System.out.println("Request: \n" + message);

			String headers = "HTTP/1.0 200 OK\n" +
					"\r\n";

			String page = "<html> " + 
					"<head><title>TEST</title></head>" + 
					"<body>This is a short test page.</body>" + 
					"</html>";

			writer.write(headers);
			writer.write(page);
			writer.flush();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
