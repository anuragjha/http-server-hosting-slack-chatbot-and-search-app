package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import handlers.FindHandler;
import handlers.Handlers;
import handlers.RequestHandler1;
import handlers.ReviewSearchHandler;

/**
 * TestServer1 class implements server
 * @author anuragjha
 *
 */
public class TestServer1 {

	private final int PORT;

	private ServerSocket server;
	private ExecutorService executer;
	private HashMap<String, Handlers> pathMapper;
	
	private volatile boolean shouldRun;

	/**
	 * Constructing Server
	 * @param port
	 */
	public TestServer1(int port)	{
		this.PORT = port;
		this.pathMapper = new HashMap<String, Handlers>();
		try {
			System.out.println("Constructing Server");
			this.server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Error in constructing server");
			System.exit(1);
		}
		
	}

	
	/**
	 * @return the pathMapper
	 */
	public HashMap<String, Handlers> getPathMapper() {
		return pathMapper;
	}
	
	
	/**
	 * addMapping method maps a request path to a specific handler
	 * @param path
	 * @param handler
	 */
	public void addMapping(String path, Handlers handler) {
		pathMapper.put(path, handler);
	}
	
	
	/**
	 * startup method starts the server
	 */
	public void startup() {
		this.shouldRun = true;
		try {
			this.runServer(this.PORT);
		} catch (IOException ioe) {
			System.out.println("Error in starting the server");
		}
		finally	{
			try {
				this.server.close();
			} catch (IOException ioe) {
				System.out.println("Error in closing the server");
			}
		}
	}


	/**
	 * runServer method implements the server and handles incoming client requests
	 * @param port
	 * @throws IOException
	 */
	private void runServer(int port) throws IOException	{

		this.executer = Executors.newFixedThreadPool(5);
		System.out.println("server started");

		while(shouldRun) {		
			Socket clientSocket = server.accept();
			Runnable RequestHandler1 = new RequestHandler1(clientSocket, this.pathMapper);
			executer.execute(RequestHandler1);	
			if(!shouldRun) {
				this.closeServer();
			}
		}
		if(!shouldRun) {
			this.closeServer();
		}

	}

	
	/**
	 * closeServer method closes the server thread pool and empties the pathMapper
	 */
	private void closeServer() {

		try {
			this.executer.shutdown();
			try {
				while(!this.executer.awaitTermination(10, TimeUnit.SECONDS)) {
					System.out.println("awaiting termination");
				}
			} catch (InterruptedException e) {
				System.out.println("Error in closing executer pool");
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally	{
			this.pathMapper = null; //at the end mapper is empty and will; have to be refilled
		}
	}



	public static void main(String[] args)	{
		TestServer ts = new TestServer(8080);
		ts.addMapping("/reviewsearch", new ReviewSearchHandler());
		ts.addMapping("/find", new FindHandler());
		System.out.println("hashmap: " + ts.getPathMapper().toString());
		ts.startup();
	}

}
