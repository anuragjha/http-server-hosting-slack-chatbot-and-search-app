/**
 * 
 */
package searchPack;

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
public class JsonQAHandler {

	/**
	 * public method to take in a QA inputFile and process it for DataStore
	 * @param inputFile
	 */
	public JsonQAHandler(String inputFile)	{
		this.jsonFileReader(inputFile);
	}



	/**
	 * jsonFileReader process QA file and then notifies DataStore
	 * record type
	 * @param inputFile
	 */
	private void jsonFileReader(String inputFile)	{

		JsonParser parser = new JsonParser();
		Path path = Paths.get(inputFile);

		try(
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				)	{
			String line;
			System.out.println("Processing QuesAns file.");

			while((line = reader.readLine()) != null)	{
				try {
					//parses each line into JsonObject
					JsonObject object =  parser.parse(line).getAsJsonObject();
					//creates AmazonQuesAns object from the Json Object
					AmazonQuesAns thisAmazonQuesAns = new Gson().fromJson(object, AmazonQuesAns.class);
					//new QA record notifies the data Store to process it
					thisAmazonQuesAns.notifyDataStore();

				} catch(JsonSyntaxException jse)	{
					System.out.println("Skipping line ...");
				}
			}	

		}	catch(IOException ioe)	{
			System.out.println("Could not process QA file");
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
