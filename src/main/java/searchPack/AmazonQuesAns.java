/**
 * 
 */
package searchPack;

/**
 * @author anuragjha
 *	AmazonQuesAns class holds the QuesAns type record 
 *  and contains method to notify DataStore
 */
public class AmazonQuesAns extends AmazonObject {

	//class variable - counter
	//private static int recordCounter = 0;    //class variable recordCounter initializing 
	// instance variable for each record
	//private int recordId;
	// data field for QuesAns type record
	//private String questionType;
	private String asin;
	//private String answerTime; // have to find correct datatype
	//private long unixTime; 
	private String question;


	//private String answerType; //in record when question type is Yes/No
	private String answer;


	/**
	 * empty constructor for Gson
	 */
	public AmazonQuesAns()	{

	}


	//getters
	/**
	 * Asin getter
	 * @return Asin
	 */
	public String getAsin() {
		return asin;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}


	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	
	/**
	 * RecordId getter
	 * @return RecordId
	 */
	public int getRecordId() {
		return recordId;
	}

	
	/**
	 * sends the new AmazonObject record to DataStore
	 */
	public void notifyDataStore() {
		this.incrementCounter();
		//notify DataStore
		AmazonDataStore.ONE.newRecord(this);
	}

	/**
	 * overriding toString method
	 * @return Text representation of AmazonQuesAns
	 */
	public String toString() {
		return " RecordId: " + this.recordId + "\n\tASIN: " + this.asin +
				"\n\tQuestion: " + this.question + "\n\tAnswer: " + this.answer;

	}


	/**
	 * method returns Text string, which contains words to be used in Word Data Structure
	 * @return Text String for Word Index
	 */
	public String getStringText()	{
		return (this.question+this.answer);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
