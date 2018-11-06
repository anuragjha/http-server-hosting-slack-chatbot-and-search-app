/**
 * 
 */
package searchPack;

/**
 * @author anuragjha
 *	AmazonReviews class holds the Review type record 
 *  and contains method to notify DataStore
 */
public class AmazonReviews extends AmazonObject {

	//class variable - counter
	//private static int recordCounter = 0;    //class variable recordCounter initializing 
	// instance variable for each record
	//private int recordId;			
	//data fields for Review record type
	private String reviewerID;
	private String asin;
	//private String reviewerName;
	//private int[] helpful; // array of 2 ints -  int[2]
	private String reviewText;
	private double overall;
	//private String summary;
	//private long unixReviewTime; 
	//private String reviewTime; //will have to find correct datatype

	/**
	 * empty constructor for Gson
	 */
	public AmazonReviews()	{

	}

	//getters
	/**
	 * Asin getter
	 * @return Asin
	 */
	public String getAsin() {
		return this.asin;
	}

	/**
	 * @return the reviewerID
	 */
	public String getReviewerID() {
		return reviewerID;
	}

	/**
	 * @return the reviewText
	 */
	public String getReviewText() {
		return reviewText;
	}

	/**
	 * @return the overall
	 */
	public double getOverall() {
		return overall;
	}

	
	/**
	 * RecordId getter
	 * @return RecordId
	 */
	public int getRecordId()	{
		return this.recordId;
	}

	
	/**
	 * sends the new AmazonObject record to DataStore
	 */
	public void notifyDataStore()	{
		this.incrementCounter();
		//notify DataStore
		AmazonDataStore.ONE.newRecord(this);
	}
	

	/**
	 * overriding toString method
	 * @return Text representation of AmazonReviews
	 */
	public String toString()	{
		return " RecordId: " + this.recordId + "\n\tReviewerId: " + this.reviewerID + 
				"\n\tASIN: " +this.asin +
				"\n\tOverall: " + this.overall + "\n\tReviewText: " + this.reviewText ;
	}

	/**
	 * method returns Text string, which contains words to be used in Word Data Structure
	 * @return Text String for Word Index
	 */
	public String getStringText()	{
		//return	this.reviewText.replaceAll("[^A-Za-z0-9]", " ").toLowerCase();  
		return this.reviewText;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
