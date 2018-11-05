package server;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is a very poorly designed HTTP server able to reply to one request with a static HTML page.
 * @author srollins
 *
 */
public class BinaryServer {

	//to test using curl: curl -X POST --data-binary @image.jpeg "http://localhost:1024"
	
	public static void main(String[] args) {
		
		try (
				ServerSocket server = new ServerSocket(1024);
				Socket sock = server.accept();
				//BufferedReader instream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				InputStream instream = sock.getInputStream();
				PrintWriter writer = new PrintWriter(sock.getOutputStream(), true)
			) {

			String message = "";
			String requestLine = oneLine(instream);
			System.out.println("requestLine: " + requestLine);
			
			int length = 0;
			if(requestLine != null)	{
				String requestHeader = oneLine(instream);
				while(requestHeader != null && !requestHeader.trim().isEmpty()) {
					
					message += requestHeader + "\n";
					requestHeader = oneLine(instream);
					
					//TODO: fix this messy hack
					if(requestHeader.startsWith("Content-Length:")) {
						length = Integer.parseInt(requestHeader.split(":")[1].trim());
					}
					//1. is this a valid format (key : value)?
					//2. is the key valid? (constants defined somewhere)
					//3. is the value valid for the key?							
				}
			}
			
			

			System.out.println("RequestHeader: \n" + message);
					
			
			byte[] bytes = new byte[length];
			int read = sock.getInputStream().read(bytes);
			
			while(read < length) {
				read += sock.getInputStream().read(bytes, read, (bytes.length-read));
			}
			
			System.out.println("Bytes expected: " + length + " Bytes read: " + read);			
			
			//save uploaded image to out.jpg
			FileOutputStream fout = new FileOutputStream("out.jpeg");
			fout.write(bytes);
			fout.close(); 
						
			//send response to client
			String headers = "HTTP/1.0 200 OK\n" +
						"\r\n";
			
			String page = "<html> " + 
						"<head><title>TEST</title></head>" + 
					    "<body>This is a short test page.</body>" + 
						"</html>";
			
			writer.write(headers);
			writer.write(page);
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Read a line of bytes until \n character.
	 * @param instream
	 * @return
	 * @throws IOException
	 */
	private static String oneLine(InputStream instream) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte b = (byte) instream.read();
		while(b != '\n') {
			bout.write(b);
			b = (byte) instream.read();
		}
		return new String(bout.toByteArray());
	}
}
