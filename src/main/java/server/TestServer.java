package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestServer {
	
	private ServerSocket server;
	private ExecutorService executer;
	private volatile boolean shouldRun = true;
	

	public TestServer(int port)	{
		this.manageServer(port);
	}

	private void manageServer(int port)	{
		try {
	 		this.runServer(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runServer(int port) throws IOException	{

		System.out.println("starting server");
		server = new ServerSocket(port);
		
		executer = Executors.newFixedThreadPool(5);
		
		while(shouldRun)	{
			
			Socket clientSocket = server.accept();
			Runnable RequestHandler = new RequestHandler(clientSocket);
			executer.execute(RequestHandler);	
			if(!shouldRun) {
				this.closeServer();
			}

		}
		
	}
	
	private void closeServer()	{
		
		try {
			this.executer.shutdown();
			try {
				while(!this.executer.awaitTermination(10, TimeUnit.SECONDS))	{
					System.out.println("awaiting termination");
				}
			} catch (InterruptedException e) {
				System.out.println("Error in closing executer pool");
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public static void main(String[] args)	{
		TestServer ts = new TestServer(8080);
	}

}
