package cs601;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import htmlGenerator.CreateContent;
import httpObjects.HttpConstants;

public class ResponseTest {

	
	@Test
	public void testGETSearchFormOK() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "GET" ,  "/reviewsearch", "query=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testGETSearchForm404() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "GET" ,  "/reviewsearchs", "query=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 404 Not Found", httpFetcher.requestLine.trim());
	}
	
	@Test
	public void testGETSearchForm1() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "GET" ,  "/reviewsearch", "query123=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
		
	
	@Test
	public void testGETFindFormOK() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "GET" ,  "/find", "asin=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testGETFindForm404() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "GET" ,  "/finds", "asin=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 404 Not Found", httpFetcher.requestLine.trim());
	}
	
	@Test
	public void testGETFindForm1() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "GET" ,  "/find", "query123=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testGETChatFormOK() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(9090, "localhost", "GET" ,  "/slackbot", "message=harness will go on");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testGETChatForm404() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(9090, "localhost", "GET" ,  "/slackbots", "message=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 404 Not Found", httpFetcher.requestLine.trim());
	}
	
	@Test
	public void testGETChatForm1() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(9090, "localhost", "GET" ,  "/slackbot", "ssage=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
 ////////
	
	@Test
	public void testPOSTSearchFormOK() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/reviewsearch", "query=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testPOSTSearchForm404() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/reviewsearchs", "query=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 404 Not Found", httpFetcher.requestLine.trim());
	}
	
	@Test
	public void testPOSTSearchForm1() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/reviewsearch", "query=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
		
	@Test
	public void testPOSTSearchForm13() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/reviewsearch", "qwerty=harness&query=ohyeah");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testPOSTFindFormOK() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/find", "asin=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testPOSTFindForm404() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/finds", "asin=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 404 Not Found", httpFetcher.requestLine.trim());
	}
	
	@Test
	public void testPOSTFindForm1() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/find", "query=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 400 Bad Request", httpFetcher.requestLine.trim());
	}
	
	@Test
	public void testPOSTFindForm13() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(8080, "localhost", "POST" ,  "/find", "asin=harness&qwerty=ohyeah");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testPOSTChatFormOK() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(9090, "localhost", "POST" ,  "/slackbot", "message=harness will go on");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testPOSTChatForm404() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(9090, "localhost", "POST" ,  "/slackbots", "message=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 404 Not Found", httpFetcher.requestLine.trim());
	}
	
	@Test
	public void testPOSTChatForm1() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(9090, "localhost", "POST" ,  "/slackbot", "message=harness&message=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 400 Bad Request", httpFetcher.requestLine.trim());
	}
	
	
	@Test
	public void testPOSTChatForm12() {

		HTTPFetcher httpFetcher = HTTPFetcher.download
				(9090, "localhost", "POST" ,  "/slackbot", "message=harness&meage=harness");
		//System.out.println(httpFetcher.requestLine);
		assertEquals("HTTP/1.0 200 OK", httpFetcher.requestLine.trim());
	}


	
	
}
