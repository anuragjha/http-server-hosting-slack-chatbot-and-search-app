/**
 * 
 */
package cs601.project1;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @author anuragjha
 *
 */
public class AmazonDataStoreSearcher {

	//variables to keep count of search results
	private int resultCount = 0;
	//private int partialResultCount = 0;
	
	// StringBuilder responseResult;

	//adding search methods here

	/**
	 * @return the responseResult
	 */
	//public StringBuilder getResponseResult() {
	//	return responseResult;
	//}


	/**
	 * @return the resultCount
	 */
	public int getResultCount() {
		return resultCount;
	}


	//finding ASIN
	/**
	 * getAsinFind method is a public method that calls asinFind method
	 * @param cmdTerm
	 */
	public LinkedList<AmazonReviews> getAsinFind(String cmdTerm)	{
		return this.asinFind(cmdTerm);
	}


	/**
	 * asinFind method finds all the Review and QA record for matching ASIN
	 * @param cmdTerm
	 */
	private LinkedList<AmazonReviews> asinFind(String cmdTerm)	{
		//this.responseResult = new StringBuilder();
		LinkedList<AmazonReviews> results = new LinkedList<AmazonReviews>();
		this.resultCount = 0;

		for(AmazonReviews review : AmazonDataStore.ONE.getReviewDataStore().values())	{
			if(review.getAsin().equalsIgnoreCase(cmdTerm))	{
				this.resultCount += 1;
				//System.out.println("\n"+review.toString());
				///outStream.write("\n"+review.toString());
				///////////////this.responseResult.append("\n"+review.toString());
				results.add(review);
			}
		}

		if(this.resultCount == 0)	{
			System.out.println("No results found");
			///this.responseResult.append("No result found");
			return null;
		}
		else	{
			System.out.println("\nResults found: "+ this.resultCount+"\n");
			return results;
		}
	}




	/**
	 * getReviewSearch method is a public method that calls reviewSearch method
	 * @param cmdTerm
	 */
	public LinkedHashMap<Integer,Integer> getReviewSearch(String cmdTerm)	{
		return this.reviewSearch(cmdTerm);
	}


	/**
	 * reviewSearch method prints out the Review Records that match the term
	 * @param cmdTerm
	 */
	private  LinkedHashMap<Integer, Integer> reviewSearch(String cmdTerm)	{

		//this.responseResult = new StringBuilder();
		this.resultCount = 0;

		if(AmazonDataStore.ONE.getReviewWordDataStore().getIndex().containsKey(cmdTerm))	{
//			for(Map.Entry<Integer, Integer> recordId :
//				AmazonDataStore.ONE.getReviewWordDataStore().searchWord(cmdTerm).createSortedOutput().entrySet())	{
//				this.resultCount += 1;
//				//System.out.println("\nSearched term: "+ cmdTerm + "\t|\tFrequency: "+recordId.getValue());
//				//System.out.println(AmazonDataStore.ONE.getReviewDataStore().get(recordId.getKey()).toString());
//				///outStream.write("\nSearched term: "+ cmdTerm + "\t|\tFrequency: "+recordId.getValue());
//				///outStream.write(AmazonDataStore.ONE.getReviewDataStore().get(recordId.getKey()).toString());
//				this.responseResult.append("\nSearched term: "+ cmdTerm + "\t|\tFrequency: "+recordId.getValue());
//				this.responseResult.append(AmazonDataStore.ONE.getReviewDataStore().get(recordId.getKey()).toString());
//			}

			System.out.println("\nResults found: "+ this.reviewSearchResults(cmdTerm).size()+"\n");
			return this.reviewSearchResults(cmdTerm);
		}
		else	{
			System.out.println("No result found");
			//this.responseResult.append("No result found");
			return null;
		}

	}
	
	
	private  LinkedHashMap<Integer, Integer> reviewSearchResults(String cmdTerm)	{

			return (LinkedHashMap<Integer, Integer>) AmazonDataStore.ONE.getReviewWordDataStore().searchWord(cmdTerm).createSortedOutput();

	}



	/**
	 * getQASearch method is a public method that calls qaSearch method
	 * @param cmdTerm
	 */
//	public void getQASearch(String cmdTerm)	{
//		this.qaSearch(cmdTerm);
//	}


	/**
	 * qaSearch method prints out the QuesAns Records that match the term
	 * @param cmdTerm
	 */
//	private void qaSearch(String cmdTerm)	{
//
//		this.resultCount = 0;
//
//		if(AmazonDataStore.ONE.getQuesAnsWordDataStore().getIndex().containsKey(cmdTerm))	{ //word in store
//			for(Map.Entry<Integer, Integer> recordId : 
//				AmazonDataStore.ONE.getQuesAnsWordDataStore().searchWord(cmdTerm).createSortedOutput().entrySet())	{
//				this.resultCount += 1;
//				System.out.println("\nSearched term: "+ cmdTerm + "\t|\tFrequency: "+recordId.getValue());
//				System.out.println(AmazonDataStore.ONE.getQuesAnsDataStore().get(recordId.getKey()).toString());
//			}
//			System.out.println("\nResults found: "+ this.resultCount+"\n");
//		}
//		else	{
//			System.out.println("No result found");
//		}
//
//	}



	/**
	 * getReviewPartialSearch method is a public method that calls reviewPartialSearch method
	 * @param cmdTerm
	 */
//	public void getReviewPartialSearch(String cmdTerm)	{
//		this.reviewPartialSearch(cmdTerm);
//	}

	/**
	 * reviewPartialSearch method prints out the Review Records that partially match the term
	 * @param cmdTerm
	 */
//	private void reviewPartialSearch(String cmdTerm)	{
//
//		this.partialResultCount = 0;
//		//
//
//		for(String word : AmazonDataStore.ONE.getReviewWordDataStore().getIndex().keySet())	{
//			if(word.contains(cmdTerm))	{
//				this.reviewSearch(word);
//				this.partialResultCount += this.resultCount;
//			}
//		}
//		if(this.partialResultCount == 0)	{
//			System.out.println("No results found");
//		}
//		else	{
//			System.out.println("\nTotal results found: "+ this.partialResultCount+"\n");
//		}
//
//	}

	/**
	 * getQAPartialSearch method is a public method that calls qaPartialSearch method
	 * @param cmdTerm
	 */
//	public void getQAPartialSearch(String cmdTerm)	{
//		this.qaPartialSearch(cmdTerm);
//	}

	/**
	 * qaPartialSearch method prints out the QuesAns Records that partially match the term
	 * @param cmdTerm
	 */
//	private void qaPartialSearch(String cmdTerm)	{
//
//		this.partialResultCount = 0;
//		for(String word : AmazonDataStore.ONE.getQuesAnsWordDataStore().getIndex().keySet())	{
//			if(word.contains(cmdTerm))	{
//				//for each word match call -> qaSearch(String cmdTerm);
//				this.qaSearch(word);
//				this.partialResultCount += this.resultCount;
//			}
//		}
//		if(this.partialResultCount == 0)	{
//			System.out.println("No results found");
//		}
//		else	{
//			System.out.println("\nTotal results found: "+ this.partialResultCount+"\n");
//		}
//
//	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
