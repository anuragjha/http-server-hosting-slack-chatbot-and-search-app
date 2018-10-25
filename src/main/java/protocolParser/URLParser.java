/**
 * 
 */
package protocolParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author anuragjha
 *
 */
public class URLParser {

	public URL url;

	public URLParser(String spec)	{
		try {
			url = new URL(spec);
		}catch(MalformedURLException mue)	{
			mue.printStackTrace();
		}
	}

	
	
	public static void main(String[] args)	{
		URLParser up = 
				new URLParser("http://127.0.0.1/reviewSearch?query=mountFuji");
		System.out.println(up.url.getPath());
		System.out.println(up.url.getQuery());
	}

}
