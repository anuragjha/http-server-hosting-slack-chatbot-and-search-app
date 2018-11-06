package searchPack;

import java.util.HashMap;


/**
 * @author anuragjha
 *	AmazonDataStore class contains all the Data Structure to hold Record details and Word details
 *  and contain methods to update those data structure, also a AmazonDataStoreSearcher Object
 */
public enum AmazonDataStore {

	ONE;

	private AmazonDataStoreSearcher searcher = new AmazonDataStoreSearcher();


	// key - recId, value - AmazonObject
	private HashMap<Integer, AmazonReviews> reviewDataStore = new HashMap<Integer, AmazonReviews>();
	private HashMap<Integer, AmazonQuesAns> quesAnsDataStore = new HashMap<Integer, AmazonQuesAns>();
	


	// key - word, value - list of recordIds and frequency 
	private InvertedIndex reviewWordDataStore = new InvertedIndex();


	/**
	 * @return the searcher
	 */
	public AmazonDataStoreSearcher getSearcher() {
		return searcher;
	}

	/**
	 * @return the reviewDataStore
	 */
	public HashMap<Integer, AmazonReviews> getReviewDataStore() {
		return reviewDataStore;
	}

	
	/**
	 * @return the quesAnsDataStore
	 */
	public HashMap<Integer, AmazonQuesAns> getQuesAnsDataStore() {
		return quesAnsDataStore;
	}
	
	
	/**
	 * @return the reviewWordDataStore
	 */
	public InvertedIndex getReviewWordDataStore() {
		return reviewWordDataStore;
	}
	

	/**
	 * newRecord method is called via notifyDataStore method of Amazon Reviews
	 * This method process the new record to 2 Review DataStores
	 * @param newRecord
	 */
	public void newRecord(AmazonReviews newRecord)	{
		processNewRecord(newRecord);
	}
	
	
	/**
	 * newRecord method is called via notifyDataStore method of AmazonQuesAns
	 * This method process the new record to 2 QuesAns DataStores
	 * @param newRecord
	 */
	public void newRecord(AmazonQuesAns newRecord)	{
		processNewRecord(newRecord);
	}

	/**
	 * processNewRecord method implements update of Review DataStores for each new Record
	 * @param newRecord
	 */
	private void processNewRecord(AmazonReviews newRecord)	{

		//updating reviewDataStore
		reviewDataStore.put(newRecord.getRecordId(), newRecord);
		//updating reviewWordDataStore
		this.reviewWordDataStore.getTextStringAndAddWords(
				newRecord.getStringText(), newRecord.getRecordId());
	}
	
	/**
	 * processNewRecord method implements update of QuesAns DataStores for each new Record
	 * @param newRecord
	 */
	private void processNewRecord(AmazonQuesAns newRecord)	{
		//updating qaDataStore
		quesAnsDataStore.put(newRecord.getRecordId(), newRecord);

	}
	
	
	public void sortAmazonWordDataStore() {
		sortReviewWordDataStore();
	}
	
	
	private void sortReviewWordDataStore() {
		
		if(reviewWordDataStore.getIndex().size() > 1) {
			for(String word : reviewWordDataStore.getIndex().keySet()) {
				reviewWordDataStore.getIndex().get(word).createSortedOutput();
			}
		}
	}
	

}
