/**
 * 
 */
package htmlGenerator;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import org.apache.commons.lang3.StringEscapeUtils;

import httpObjects.HttpConstants;
import searchPack.AmazonDataStore;
import searchPack.AmazonQuesAns;
import searchPack.AmazonReviews;

/**
 * @author anuragjha
 * CreateContent class creates response for a particular request
 */
public class CreateContent {

	private StringBuilder responseHeader;
	private StringBuilder responseBody;

	/**
	 * constructor
	 */
	public CreateContent() {
		this.responseHeader = new StringBuilder();
		this.responseBody = new StringBuilder();
	}

	/**
	 * getContentLength method gets the content length
	 * @param responseBody
	 * @return
	 */
	public int getContentLength(String responseBody) {
		int contentLength = 0;
		try {
			contentLength  = responseBody.getBytes("UTF-8").length;
			return contentLength;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unable to get Content-Length");
			return contentLength;
		}

	}

	/**
	 * buildReviewSearchResult method builds search results for review
	 * @param queryTerm
	 * @return
	 */
	public String buildReviewSearchResult(String queryTerm) {
		String[] terms = queryTerm.split("\\+");
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Search Results");
		this.writeHtmlBodyStart();
		System.out.println("in buildReviewSearchResult");
		for(String term : terms) {
			this.writeReviewSearchResult(term.replaceAll("[^A-Za-z0-9]", "").toLowerCase());
		}

		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");

		return this.responseHeader.toString() + this.responseBody.toString();
	}


	/**
	 * buildFindResult methods builds search results for asin
	 * @param queryTerm
	 * @return
	 */
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

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");

		return this.responseHeader.toString() + this.responseBody.toString();

	}

	/**
	 * buildFindForm methods builds the form for Find asin
	 * @return
	 */
	public String buildFindForm() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Search Amazon Rewiews");
		this.writeHtmlBodyStart();
		this.writeFindAsinForm();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");

		return this.responseHeader.toString() + this.responseBody.toString();
	}

	/**
	 * buildReviewSearchForm method builds the form for Search Reviews
	 * @return
	 */
	public String buildReviewSearchForm() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Search Amazon Rewiews");
		this.writeHtmlBodyStart();
		this.writeReviewSearchForm();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");

		return this.responseHeader.toString() + this.responseBody.toString();
	}


	/**
	 * buildChatForm method builds the form for Slack chat
	 * @return
	 */
	public String buildChatForm() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Slack chat");
		this.writeHtmlBodyStart();
		this.writeChatForm();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");

		return this.responseHeader.toString() + this.responseBody.toString();
	}


	/**
	 * buildChatFormAfterSuccess method builds response for when the message is delivered successfully
	 * @return
	 */
	public String buildChatFormAfterSuccess() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Slack chat");
		this.writeHtmlBodyStart();
		this.writeSlackSuccess();
		this.writeChatForm();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");
		return this.responseHeader.toString() + this.responseBody.toString();
	}


	/**
	 * buildChatFormAfterFail method response for when the message is not delivered sucessfully
	 * @return
	 */
	public String buildChatFormAfterFail() {
		this.responseHeader.append(HttpConstants.OK);
		this.writeHtmlHead("Slack chat");
		this.writeHtmlBodyStart();
		this.writeSlackFail();
		this.writeChatForm();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");
		return this.responseHeader.toString() + this.responseBody.toString();
	}


	/**
	 * buildContent404 method builds content for 404 request
	 * @return
	 */
	public String buildContent404() {
		this.responseHeader.append(HttpConstants.PAGENOTFOUND);
		this.writeHtmlHead("Not Found");
		this.writeHtmlBodyStart();
		this.write404Response();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");
		return this.responseHeader.toString() + this.responseBody.toString();

	}

	/**
	 * buildContent405 method builds content for 405 request
	 * @return
	 */
	public String buildContent405() {
		this.responseHeader.append(HttpConstants.METHODNOTFOUND);
		this.writeHtmlHead("405 Method Not Supported");
		this.writeHtmlBodyStart();
		this.write405Response();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");
		return this.responseHeader.toString() + this.responseBody.toString();

	}

	/**
	 * buildContent400 method builds content for 400 request
	 * @return
	 */
	public String buildContent400() {
		this.responseHeader.append(HttpConstants.BADREQUEST);
		this.writeHtmlHead("400 Bad Request");
		this.writeHtmlBodyStart();
		this.write400Response();
		this.writeHtmlBodyEnd();

		//getting content length
		this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");
		return this.responseHeader.toString() + this.responseBody.toString();

	}

	/**
	 * writeHtmlHead writes HTML head
	 * @param title
	 */
	private void writeHtmlHead(String title)	{
		this.responseBody.append("<!DOCTYPE html>");
		this.responseBody.append("<html lang=\"en\">");
		this.responseBody.append("<head>");
		this.responseBody.append("<meta charset=\"utf-8\"/>");		
		this.responseBody.append("<title>" + title+ "</title>");		   		  
		this.responseBody.append("</head>");
	}

	/**
	 * writeHtmlBodyStart method writes HTML body start
	 */
	private void writeHtmlBodyStart()	{
		this.responseBody.append("<body>");		
		//this.responseBody.append("<div class=\"container\">");
	}

	/**
	 * writeHtmlBodyEnd method writes HTML body end
	 */
	private void writeHtmlBodyEnd() {
		this.responseBody.append("</body>");
		this.responseBody.append("</html>");	
	}

	/**
	 * writeSlackSuccess method writes HTML for success slack message
	 */
	private void writeSlackSuccess() {
		this.responseBody.append("<h3>Message sent successfully</h3>");

	}

	/**
	 * writeSlackFail method writes HTML for unsuccess slack message
	 */
	private void writeSlackFail() {
		this.responseBody.append("<h3>Message NOT sent</h3>");

	}

	/**
	 * writeFindAsinForm method writes HTML form for Asin find
	 */
	private void writeFindAsinForm() {
		this.responseBody.append("<h3>Search Asins in Amazon Reviews</h3>");	
		this.responseBody.append("<form action=\"/find\" method=\"post\">");	
		this.responseBody.append("<label name=\"findAsin\" value=\"findAsin\">Find Asin</label> <br/>");	
		this.responseBody.append("<input type=\"text\" name=\"asin\" /> <br/>");
		this.responseBody.append("<input type=\"submit\" value=\"Submit\" />");	    
		this.responseBody.append("</form>");		       
	}

	/**
	 * writeReviewSearchForm method writes HTML form for Review Search
	 */
	private void writeReviewSearchForm() {
		this.responseBody.append("<h3>Search Term in Amazon Reviews</h3>");	
		this.responseBody.append("<form action=\"/reviewsearch\" method=\"post\">");	
		this.responseBody.append("<label name=\"searchTerm\" value=\"searchTerm\">Search Term</label> <br/>");	
		this.responseBody.append("<input type=\"text\" name=\"query\" /> <br/>");
		this.responseBody.append("<input type=\"submit\" value=\"Submit\" />");	    
		this.responseBody.append("</form>");

	}

	/**
	 * writeChatForm method writes HTML form for Slack Chat
	 */
	private void writeChatForm() {
		this.responseBody.append("<h3>Send message on Slack</h3>");	
		this.responseBody.append("<form action=\"/slackbot\" method=\"post\">");	
		this.responseBody.append("<label name=\"slackMessage\" value=\"slackMessage\">Send Message</label> <br/>");	
		this.responseBody.append("<input type=\"text\" name=\"message\" /> <br/>");
		this.responseBody.append("<input type=\"submit\" value=\"Submit\" />");	    
		this.responseBody.append("</form>");

	}

	/**
	 * write404Response method writes HTML form for 404
	 */
	private void write404Response()	{
		this.responseBody.append("<h1>Page Not Found</h1>");	
		this.responseBody.append("<p>Sorry, but the page you were trying to view does not exist.</p>");		
	}

	/**
	 * write404Response method writes HTML form for 400
	 */
	private void write400Response()	{
		this.responseBody.append("<h1>Bad Request</h1>");	
		this.responseBody.append("<p>Sorry, but the request was incorrect.</p>");		
	}

	/**
	 * write405Response method writes HTML form for 405
	 */
	private void write405Response()	{
		this.responseBody.append("<h1>Method Not Supported</h1>");	
		this.responseBody.append("<p>Sorry, but the method you were trying to use is not yet supported.</p>");		
	}

	/**
	 * writeHtmlTableStartReviewsResult writes HTML table headers for Review Search
	 */
	private void writeHtmlTableStartReviewsResult()	{
		this.responseBody.append("<table style=\"width:100%\">");
		this.responseBody.append("<tr>");
		this.responseBody.append("<th>Reviewer ID</th>");
		this.responseBody.append("<th>Asin</th>");
		this.responseBody.append("<th>Review Text</th>");
		this.responseBody.append("<th>Overall</th>");
		this.responseBody.append("</tr>");

	}

	/**
	 * writeHtmlTableEnd writes HTML tables closing
	 */
	private void writeHtmlTableEnd()	{
		this.responseBody.append("</table>");
	}

	/**
	 * writeReviewSearchResult method writes HTML review search results 
	 * @param queryTerm
	 */
	private void writeReviewSearchResult(String queryTerm) {
		System.out.println("in writeReviewSearchResult");

		if((AmazonDataStore.ONE.getSearcher().getReviewSearch(queryTerm)) != null)	{
			this.responseBody.append("<h3>Results of Search in Amazon Reviews</h3>");
			this.responseBody.append("<h4>Search Term is - " + queryTerm + "</h4>");
			this.writeHtmlTableStartReviewsResult();
			
			int count = 0;
			for(AmazonReviews thisReview :
				AmazonDataStore.ONE.getReviewWordDataStore().searchWord(queryTerm).getSortedInvertedIndexValues())	{
				this.responseBody.append("<tr>");
				this.responseBody.append("<td>"+ thisReview.getReviewerID() +"</td>");
				this.responseBody.append("<td>"+ thisReview.getAsin() +"</td>");
				this.responseBody.append("<td>"+ StringEscapeUtils.escapeHtml4(thisReview.getReviewText()) +"</td>");
				this.responseBody.append("<td>"+ thisReview.getOverall() +"</td>");
				this.responseBody.append("</tr>");
				count = count + 1;
				if(count >= 1000) {
					break;
				}
			}

			this.writeHtmlTableEnd();

		} else	{
			this.responseBody.append("<h3>No result found in Review files</h3>");
		}


	}


	/**
	 * writeHtmlTableFindResult writes HTML table headers for Asin find in Reviews
	 */
	private void writeHtmlTableFindResult()	{

		this.responseBody.append("<table style=\"width:100%\">");
		this.responseBody.append("<tr>");
		this.responseBody.append("<th>Reviewer ID</th>");
		this.responseBody.append("<th>Asin</th>");
		this.responseBody.append("<th>Review Text</th>");
		this.responseBody.append("<th>Overall</th>");
		this.responseBody.append("</tr>");

	}

	/**
	 * writeHtmlTableFindResultForQA writes HTML table headers for Asin search of QA
	 */
	private void writeHtmlTableFindResultForQA()	{

		this.responseBody.append("<table style=\"width:100%\">");
		this.responseBody.append("<tr>");
		this.responseBody.append("<th>Asin</th>");
		this.responseBody.append("<th>Question</th>");
		this.responseBody.append("<th>Answer</th>");
		this.responseBody.append("</tr>");

	}


	/**
	 * writeFindResult method writes HTML Asin Find results
	 * @param queryTerm
	 */
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
				this.responseBody.append("<td>"+ StringEscapeUtils.escapeHtml4(eachResult.getReviewText()) +"</td>");
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
				this.responseBody.append("<td>"+ StringEscapeUtils.escapeHtml4(eachResult.getQuestion()) +"</td>");
				this.responseBody.append("<td>"+ StringEscapeUtils.escapeHtml4(eachResult.getAnswer()) +"</td>");
				this.responseBody.append("</tr>");	
			}
			this.writeHtmlTableEnd();
		} else {
			this.responseBody.append("<h3>No result found in QA files</h3>");
		}

	}



	public static void main(String[] args) {

	}



}
