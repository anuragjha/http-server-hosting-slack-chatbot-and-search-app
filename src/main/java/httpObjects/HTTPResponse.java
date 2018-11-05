/**
 * 
 */
package httpObjects;

import java.util.EnumMap;
import java.util.HashMap;

import htmlGenerator.CreateContent;
import htmlGenerator.HttpConstants;

/**
 * @author anuragjha
 *
 */
public class HTTPResponse {



	private StringBuilder responseHeader;
	private StringBuilder responseBody;
	private HashMap<String,String> queries;
	String queryTerm; //TODO: change the passing of query to queries HASHMAP
	private String responseType;

	public CreateContent createContent;
	
	public String content = "";

	public HTTPResponse() {
		createContent = new CreateContent();
	}
	
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	

	private String buildContent()	{

		switch(this.responseType) {
		case "/" :
			createContent.buildContentHome();
			break;
		case "404" :
			createContent.buildContent404();
			break;
		case "405" :
			createContent.buildContent405();
			break;
		case "/reviewsearch" :
			if(this.queryTerm.equals("")) {
				createContent.buildReviewSearchForm();	
			} else {
				createContent.buildReviewSearchResult(this.queryTerm);
			}

			break;
		case "/find" :
			if(this.queryTerm.equals("")) {
				createContent.buildFindForm();
			} else {
				createContent.buildFindResult(this.queryTerm);
			}


			break;
		case "/slackbot" :
			if(this.queryTerm.equals("")) {
				createContent.buildChatForm();	
			} else {
				//.buildReviewSearchResult(this.queryTerm);
			}

			break;
		}
		//System.out.println(this.responseHeader.toString() + this.responseBody.toString());
		return this.responseHeader.toString() + this.responseBody.toString(); 

	}

}


