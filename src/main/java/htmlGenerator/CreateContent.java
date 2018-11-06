/**
 * 
 */
package htmlGenerator;

import java.util.LinkedList;
import java.util.Map;

import httpObjects.HttpConstants;
import searchPack.AmazonDataStore;
import searchPack.AmazonQuesAns;
import searchPack.AmazonReviews;

/**
 * @author anuragjha
 *
 */
public class CreateContent {

	private StringBuilder responseHeader;
	private StringBuilder responseBody;
	private String responseType;
	private String queryTerm;

	public CreateContent() {
		this.responseHeader = new StringBuilder();
		this.responseBody = new StringBuilder();
		//this.responseType = responseType;
		this.responseType = "";
		this.queryTerm = "";

	}

	public CreateContent(String responseType) {
		this.responseHeader = new StringBuilder();
		this.responseBody = new StringBuilder();
		this.responseType = responseType;

		this.queryTerm = "";

	}

	public CreateContent(String responseType, String queryTerm) {
		this.responseHeader = new StringBuilder();
		this.responseBody = new StringBuilder();
		this.responseType = responseType;

		this.queryTerm = queryTerm;
	}


	public String buildReviewSearchResult(String queryTerm) {
		String[] terms = queryTerm.split("\\+");
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Search Results");
		this.writeHtmlBodyStart();
		System.out.println("in buildReviewSearchResult");
		for(String term : terms) {
			this.writeReviewSearchResult(term.replaceAll("[^A-Za-z0-9]", "").toLowerCase());
		}
		//this.writeReviewSearchResult(queryTerm);

		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();
	}

	public String buildFindResult(String queryTerm) {
		String[] terms = queryTerm.split("\\+");
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Find Results");
		this.writeHtmlBodyStart();
		System.out.println("in buildFindResult");
		for(String term : terms) {
			this.writeFindResult(term.replaceAll("[^A-Za-z0-9]", "").toLowerCase());
		}

		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();

	}


	public String buildFindForm() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Search Amazon Rewiews");
		this.writeHtmlBodyStart();
		this.writeFindAsinForm();
		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();
	}

	public String buildReviewSearchForm() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Search Amazon Rewiews");
		this.writeHtmlBodyStart();
		this.writeReviewSearchForm();
		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();

	}

	public String buildChatForm() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Slack chat");
		this.writeHtmlBodyStart();
		this.writeChatForm();
		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();
	}
	
	public String buildChatFormAfterSuccess() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Slack chat");
		this.writeHtmlBodyStart();
		this.writeSlackSuccess();
		this.writeChatForm();
		this.writeHtmlBodyEnd();
		
		return this.responseHeader.toString() + this.responseBody.toString();
	}
	
	
	public String buildChatFormAfterFail() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Slack chat");
		this.writeHtmlBodyStart();
		this.writeSlackFail();
		this.writeChatForm();
		this.writeHtmlBodyEnd();
		
		return this.responseHeader.toString() + this.responseBody.toString();
	}



	public String buildContent404() {
		this.responseHeader.append(HttpConstants.PAGENOTFOUND);
		this.writeHtmlHead("Not Found");
		this.writeHtmlBodyStart();
		this.write404Response();
		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();

	}

	public String buildContent405() {
		//this.responseHeader.append("HTTP/1.0 405 Method Not Allowed\n" + "\r\n");
		this.responseHeader.append(HttpConstants.METHODNOTFOUND);
		this.writeHtmlHead("405 Method Not Supported");
		this.writeHtmlBodyStart();
		this.write405Response();
		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();

	}
	
	
	public String buildContent400() {
		//this.responseHeader.append("HTTP/1.0 405 Method Not Allowed\n" + "\r\n");
		this.responseHeader.append(HttpConstants.BADREQUEST);
		this.writeHtmlHead("400 Bad Request");
		this.writeHtmlBodyStart();
		this.write400Response();
		this.writeHtmlBodyEnd();

		return this.responseHeader.toString() + this.responseBody.toString();

	}


	private void writeHtmlHead(String title)	{
		this.responseBody.append("<!doctype html>");
		this.responseBody.append("<html lang=\"en\">");
		this.responseBody.append("<head>");
		this.responseBody.append("<meta charset=\"utf-8\"/>");		
		this.responseBody.append("<title>" + title+ "</title>");		  
		this.responseBody.append("<meta name="+title+ "content="+title+"  />");
		this.responseBody.append("<meta name=\"Anurag\" content=" + title+ "/>");	  
		//this.responseBody.append("<link rel=\"stylesheet\" href=\"src⁩/main⁩/resources⁩/css/styles.css?v=1.0\">");		  
		this.responseBody.append("</head>");
	}

	private void writeHtmlBodyStart()	{
		this.responseBody.append("<body>");		
		this.responseBody.append("<div class=\"container\">");
	}


	private void writeHtmlBodyEnd() {
		//this.responseBody.append("<script src=\"src⁩/main⁩/resources⁩/js⁩/scripts.js\"></script>"); 
		this.responseBody.append("</body>");

		this.responseBody.append("</html>");	

	}

	private void writeSlackSuccess() {
		this.responseBody.append("<h3>Message sent successfully</h3>");
		
	}
	
	private void writeSlackFail() {
		this.responseBody.append("<h3>Message NOT sent</h3>");
		
	}
	

	private void writeFindAsinForm() {
		this.responseBody.append("<h3>Search Asins in Amazon Reviews</h3>");	
		this.responseBody.append("<form action=\"/find\" method=\"post\">");	
		this.responseBody.append("<label name=\"findAsin\" value=\"findAsin\">Find Asin</label> <br/>");	
		this.responseBody.append("<input type=\"text\" name=\"asin\" /> <br/>");
		this.responseBody.append("<input type=\"submit\" value=\"Submit\" />");	    
		this.responseBody.append("</form>");		      
		this.responseBody.append("</div>"); 

	}

	private void writeReviewSearchForm() {
		this.responseBody.append("<h3>Search Term in Amazon Reviews</h3>");	
		this.responseBody.append("<form action=\"/reviewsearch\" method=\"post\"/>");	
		this.responseBody.append("<label name=\"searchTerm\" value=\"searchTerm\">Search Term</label> <br/>");	
		this.responseBody.append("<input type=\"text\" name=\"query\" /> <br/>");
		this.responseBody.append("<input type=\"submit\" value=\"Submit\" />");	    
		this.responseBody.append("</form>");

	}

	private void writeChatForm() {
		this.responseBody.append("<h3>Send message on Slack</h3>");	
		this.responseBody.append("<form action=\"/slackbot\" method=\"post\"/>");	
		this.responseBody.append("<label name=\"slackMessage\" value=\"slackMessage\">Send Message</label> <br/>");	
		this.responseBody.append("<input type=\"text\" name=\"message\" /> <br/>");
		this.responseBody.append("<input type=\"submit\" value=\"Submit\" />");	    
		this.responseBody.append("</form>");

	}

	private void write404Response()	{
		this.responseBody.append("<h1>Page Not Found</h1>");	
		this.responseBody.append("<p>Sorry, but the page you were trying to view does not exist.</p>");		
	}
	
	private void write400Response()	{
		this.responseBody.append("<h1>Bad Request</h1>");	
		this.responseBody.append("<p>Sorry, but the request was incorrect.</p>");		
	}


	private void write405Response()	{
		this.responseBody.append("<h1>Method Not Supported</h1>");	
		this.responseBody.append("<p>Sorry, but the method you were trying to use is not yet supported.</p>");		
	}


	private void writeHtmlTableStartReviewsResult()	{
		this.responseBody.append("<table style=\"width:100%\">");
		this.responseBody.append("<tr>");
		this.responseBody.append("<th>Reviewer ID</th>");
		this.responseBody.append("<th>Asin</th>");
		this.responseBody.append("<th>Review Text</th>");
		this.responseBody.append("<th>Overall</th>");
		this.responseBody.append("<th>Frequency</th>");
		this.responseBody.append("</tr>");

	}


	private void writeHtmlTableEnd()	{
		this.responseBody.append("</table>");
	}

	private void writeReviewSearchResult(String queryTerm) {
		System.out.println("in writeReviewSearchResult");
		//int resultCount = 0;

		if((AmazonDataStore.ONE.getSearcher().getReviewSearch(queryTerm)) != null)	{
			this.responseBody.append("<h3>Results of Search in Amazon Reviews</h3>");
			this.writeHtmlTableStartReviewsResult();

			this.responseBody.append("<h3>Search Term is - " + queryTerm + "</h3>");
			for(Map.Entry<Integer, Integer> recordId :
				AmazonDataStore.ONE.getReviewWordDataStore().searchWord(queryTerm).createSortedOutput().entrySet())	{
				AmazonReviews thisReview = AmazonDataStore.ONE.getReviewDataStore().get(recordId.getKey());
				this.responseBody.append("<tr>");
				this.responseBody.append("<td>"+ thisReview.getReviewerID() +"</td>");
				this.responseBody.append("<td>"+ thisReview.getAsin() +"</td>");
				this.responseBody.append("<td>"+ thisReview.getReviewText() +"</td>");
				this.responseBody.append("<td>"+ thisReview.getOverall() +"</td>");
				this.responseBody.append("<td>"+ recordId.getValue() +"</td>");
				this.responseBody.append("</tr>");

			}
			this.writeHtmlTableEnd();

		} else	{
			this.responseBody.append("<h3>No result found in Review files</h3>");
		}

		//this.responseBody.append(AmazonDataStore.ONE.getSearcher().getReviewSearch(queryTerm));
		//		

	}



	private void writeHtmlTableFindResult()	{

		this.responseBody.append("<table style=\"width:100%\">");
		this.responseBody.append("<tr>");
		this.responseBody.append("<th>Reviewer ID</th>");
		this.responseBody.append("<th>Asin</th>");
		this.responseBody.append("<th>Review Text</th>");
		this.responseBody.append("<th>Overall</th>");
		this.responseBody.append("</tr>");

	}


	private void writeHtmlTableFindResultForQA()	{

		this.responseBody.append("<table style=\"width:100%\">");
		this.responseBody.append("<tr>");
		this.responseBody.append("<th>Asin</th>");
		this.responseBody.append("<th>Question</th>");
		this.responseBody.append("<th>Answer</th>");
		this.responseBody.append("</tr>");

	}



	private void writeFindResult(String queryTerm) {
		System.out.println("in writeFindResult");

		LinkedList<AmazonReviews> resultsRev = AmazonDataStore.ONE.getSearcher().getAsinFindReviews(queryTerm);
		if(resultsRev != null)	{
			this.responseBody.append("<h3>Results of Find in Amazon Reviews</h3>");
			this.writeHtmlTableFindResult();

			for(AmazonReviews eachResult : resultsRev)	{
				this.responseBody.append("<tr>");
				this.responseBody.append("<td>"+ eachResult.getReviewerID() +"</td>");
				this.responseBody.append("<td>"+ eachResult.getAsin() +"</td>");
				this.responseBody.append("<td>"+ eachResult.getReviewText() +"</td>");
				this.responseBody.append("<td>"+ eachResult.getOverall() +"</td>");
				this.responseBody.append("</tr>");	
			}
			//this.responseBody.append("</table>");
			this.writeHtmlTableEnd();

		} else {
			this.responseBody.append("<h3>No result found in Review files</h3>");
		}


		LinkedList<AmazonQuesAns> resultsQa = AmazonDataStore.ONE.getSearcher().getAsinFindQAs(queryTerm);
		if(resultsQa != null)	{
			this.writeHtmlTableFindResultForQA();
			for(AmazonQuesAns eachResult : resultsQa)	{
				this.responseBody.append("<tr>");
				this.responseBody.append("<td>"+ eachResult.getAsin() +"</td>");
				this.responseBody.append("<td>"+ eachResult.getQuestion() +"</td>");
				this.responseBody.append("<td>"+ eachResult.getAnswer() +"</td>");
				this.responseBody.append("</tr>");	
			}
			this.writeHtmlTableEnd();
		} else {
			this.responseBody.append("<h3>No result found in QA files</h3>");
		}

	}






}
