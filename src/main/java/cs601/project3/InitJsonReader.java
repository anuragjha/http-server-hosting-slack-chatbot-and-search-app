/**
 * 
 */
package cs601.project3;

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
public class InitJsonReader {

	private static SearchInit searchInit = null;
	private static ChatInit chatInit = null;

	/**
	 * jsonFileReader process Review file and then notifies DataStore 
	 * @param inputFile
	 */
	public static Object project3InitJsonReader(String file, Class<?> initClass)	{

		JsonParser parser = new JsonParser();
		Path path = Paths.get(file);	


		try(
				BufferedReader reader = Files.newBufferedReader(path, Charset.forName("ISO-8859-1"))
				)	{
			String line;
			//System.out.println("Processing " + "project2Init.json" + " file.");

			//while((line = reader.readLine()) != null)	{
			if((line = reader.readLine()) != null)	{
				try {
					//parses each line into JsonObject
					JsonObject object =  parser.parse(line).getAsJsonObject();
					//creates Init object from the Json Object 
					if(initClass == SearchInit.class) {
						searchInit = new Gson().fromJson(object, SearchInit.class);
					} else if(initClass == ChatInit.class) {
						chatInit = new Gson().fromJson(object, ChatInit.class);
					}


				} catch(JsonSyntaxException jse)	{
					System.out.println("Project3 init reader - Skipping line ...");
				}
			}	

		}	catch(IOException ioe)	{
			System.out.println("Could not process init file");
			System.out.println("Exiting System");
			System.exit(0);
		}

		if(initClass == SearchInit.class) {
			return searchInit;
		} else if(initClass == ChatInit.class) {
			return chatInit;
		}
		else {
			return null;
		}

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		//Project2Init init = Project2InitReader.project2InitjsonReader();
		//System.out.println("init: " + init);

	}

}
