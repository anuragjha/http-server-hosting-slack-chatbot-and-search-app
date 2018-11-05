/**
 * 
 */
package cs601.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * @author anuragjha
 *
 */
public class JsonReviewHandler {

	/**
	 * public method to take in a Review inputFile and process it for DataStore
	 * @param inputFile
	 */
	public JsonReviewHandler(String inputFile)	{
		this.jsonFileReader(inputFile);
	}



	/**
	 * jsonFileReader process Review file and then notifies DataStore 
	 * @param inputFile
	 */
	private  void jsonFileReader(String inputFile)	{

		JsonParser parser = new JsonParser();
		Path path = Paths.get(inputFile);	


		try(
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				)	{
			String line;
			System.out.println("Processing Review file.");

			while((line = reader.readLine()) != null)	{
				try {
					//parses each line into JsonObject
					JsonObject object =  parser.parse(line).getAsJsonObject();
					//creates AmazonReviews object from the Json Object
					AmazonReviews thisAmazonReview = new Gson().fromJson(object, AmazonReviews.class);
					//new Review record notifies the data Store to process it
					thisAmazonReview.notifyDataStore();

				} catch(JsonSyntaxException jse)	{
					System.out.println("Skipping line ...");
				}
			}	

		}	catch(IOException ioe)	{
			System.out.println("Could not process Review file");
			System.out.println("Exiting System");
			System.exit(0);
		}

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
