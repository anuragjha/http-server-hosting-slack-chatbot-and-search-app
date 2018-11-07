import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import httpObjects.RequestLineParser;

public class RequestParseTest {

	@Test
	public void checkRequestMethod() {
		RequestLineParser rp = new RequestLineParser("GET /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals("GET", rp.getRequestLineMethod());
	}
	
	@Test
	public void checkRequestMethod1() {
		RequestLineParser rp = new RequestLineParser("GET 	/?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals("GET", rp.getRequestLineMethod());
	}
	
	
	@Test
	public void checkRequestMethod2() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals("POST", rp.getRequestLineMethod());
	}
	
	@Test
	public void checkRequestMethod3() {
		RequestLineParser rp = new RequestLineParser("POST    /?searchIn=reviewsSea  rch&query=abc HTTP/1.1");
		assertEquals("", rp.getRequestLineMethod());
	}
	
	@Test
	public void checkRequestMethod4() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abcHTTP/1.1");
		assertEquals("", rp.getRequestLineMethod());
	}
	
	@Test
	public void checkRequestMethod5() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals("POST", rp.getRequestLineMethod());
	}
	
	
	@Test
	public void checkRequestMethod6() {
		RequestLineParser rp = new RequestLineParser("POST /?searc hIn=reviewsSearch&query=abcHTTP/1.1");
		assertEquals("", rp.getRequestLineMethod());
	}
	
	//
	@Test
	public void checkRequestHTTP() {
		RequestLineParser rp = new RequestLineParser("GET /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals(true , rp.checkIfHttpVersion1());
	}

	
	@Test
	public void checkRequestHTTP1() {
		RequestLineParser rp = new RequestLineParser("GET 	/?searchIn=reviewsSearch&query=abc HTTP/1.0");
		assertEquals(true, rp.checkIfHttpVersion0());
	}
	
	
	@Test
	public void checkRequestHTTP2() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals(true, rp.checkIfHttpVersion1());
	}
	
	@Test
	public void checkRequestHTTP3() {
		RequestLineParser rp = new RequestLineParser("POST    /?searchIn=reviewsSea  rch&query=abc HTTP/1.0");
		assertEquals(false, rp.checkIfHttpVersion0());
	}
	
	@Test
	public void checkRequestHTTP4() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abcHTTP/1.1");
		assertEquals(false, rp.checkIfHttpVersion1());
	}
	
	@Test
	public void checkRequestHTTP5() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals(true, rp.checkIfHttpVersion1());
	}
	
	
	@Test
	public void checkRequestHTTP6() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abc HTTP/2.1");
		assertEquals(false, rp.checkIfHttpVersion1());
	}
	//
	
	
	@Test
	public void checkRequestPath() {
		RequestLineParser rp = new RequestLineParser("POST /?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals("/", rp.getRequestLinePath());
	}
	
	
	@Test
	public void checkRequestPath1() {
		RequestLineParser rp = new RequestLineParser("POST /bla?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals("/bla", rp.getRequestLinePath());
	}
	
	@Test
	public void checkRequestPath2() {
		RequestLineParser rp = new RequestLineParser("POST /bla?searchIn= reviewsSearch&query=abcHTTP/1.1");
		assertEquals("", rp.getRequestLinePath());
	}
	
	
	@Test
	public void checkRequestPath3() {
		RequestLineParser rp = new RequestLineParser("GET /bla?searchIn =reviewsSearch&query=abc HTTP/1.1");
		assertEquals("", rp.getRequestLinePath());
	}
	
	
	@Test
	public void checkRequestPath4() {
		RequestLineParser rp = new RequestLineParser("GET /bla&bla?searchIn=reviewsSearch&query=abc HTTP/1.1");
		assertEquals("/bla&bla", rp.getRequestLinePath());
	}
	
	
	@Test
	public void checkRequestQueries() {
		RequestLineParser rp = new RequestLineParser("GET /bla&bla?searchIn=reviewsSearch&query=abc HTTP/1.1");
		HashMap<String, String> kV = new HashMap<String,String>(rp.getRequestLineQueries());
		String keyToCheck = "";
		for(String key : kV.keySet()) {
			if(key.equals("searchIn")) {
				keyToCheck = key;
			}
		}
		assertEquals("searchIn", keyToCheck);
	}
	
	@Test
	public void checkRequestQueries1() {
		RequestLineParser rp = new RequestLineParser("GET /bla&bla?searchIn=reviewsSearch&query=abc HTTP/1.1");
		HashMap<String, String> kV = new HashMap<String,String>(rp.getRequestLineQueries());
		String keyToCheck = "";
		for(String key : kV.keySet()) {
			if(key.equals("query")) {
				keyToCheck = key;
			}
		}
		assertEquals("query", keyToCheck);
	}
	
	
	@Test
	public void checkRequestQueries2() {
		RequestLineParser rp = new RequestLineParser("GET /bla&bla?searchIn=reviewsSearch&query=abc HTTP/1.1");
		HashMap<String, String> kV = new HashMap<String,String>(rp.getRequestLineQueries());
		String valueToCheck = "";
		for(String key : kV.keySet()) {
			if(key.equals("query")) {
				valueToCheck = kV.get(key);
			}
		}
		assertEquals("abc", valueToCheck);
	}
	
	@Test
	public void checkRequestQueries3() {
		RequestLineParser rp = new RequestLineParser("GET /bla&bla?searchIn=reviewsSearch&query=a bcHTTP/1.1");
		HashMap<String, String> kV = rp.getRequestLineQueries();

		assertEquals(null, kV);
	}
	
	@Test
	public void checkRequestQueries4() {
		RequestLineParser rp = new RequestLineParser("GET /bla&bla?searchIn=reviewsSearch|query=abc HTTP/1.1");
		HashMap<String, String> kV = rp.getRequestLineQueries();

		String valueToCheck = "";
		for(String key : kV.keySet()) {
			if(key.equals("searchIn")) {
				valueToCheck = kV.get(key);
			}
		}
		assertEquals("reviewsSearch|query", valueToCheck);
	}
	
	
	@Test
	public void checkRequestQueries5() {
		RequestLineParser rp = new RequestLineParser("GET /bla&bla?searchIn=reviewsSearch||query=abc HTTP/1.1");
		HashMap<String, String> kV = rp.getRequestLineQueries();

		String valueToCheck = "";
		for(String key : kV.keySet()) {
			if(key.equals("query")) {
				valueToCheck = kV.get(key);
			}
		}
		assertEquals("", valueToCheck);
	}
	
	
}
