/**
 * 
 */
package searchPack;

import java.util.HashMap;

/**
 * InvertedIndex class implements the Inverted Index needed to build Word Data Store.
 * This class stores word as key and keeps track of recordId where the word occurs 
 * and its frequency
 * @author anuragjha
 */
public class InvertedIndex {

	private HashMap<String, AmazonWordDetails> invertedIndex;

	/**
	 * constructor for the InvertedIndex class
	 */
	public InvertedIndex()	{
		this.invertedIndex = new HashMap<String, AmazonWordDetails>();
	}

	//getter
	/**
	 * @return invertedIndex
	 */
	public HashMap<String, AmazonWordDetails> getIndex() {
		return invertedIndex;
	}


	/**
	 * searchWord method takes in a word and returns the recordIds associated with the word
	 * @param word
	 * @return LinkedHashMap of RecordIds and Frequency for the word
	 */
	public AmazonWordDetails searchWord(String word)	{

		// build a datatype for this ==> this.invertedIndex.get(word) to have sorted result
		return this.invertedIndex.get(word);
	}


	/**
	 * getTextString method breaks the TextString into words and sends it to add method
	 * @param newString
	 * @param recordId
	 */
	public void getTextStringAndAddWords(String newString, int recordId)	{
		String[] newWordStringArray = newString.split(" ");
		if(newWordStringArray != null) {
			for(String word : newWordStringArray)	{
				if((word != null) && (word != ""))	{
					String newWord = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
					if(newWord.length() > 0)	{
						add(newWord, recordId); // update InvertedIndex for the particular word
					}
				}
			}
		}

	}



	/**
	 * add method takes in a word and recordId adds it into a InvertedIndex
	 * @param word
	 * @param recordId
	 */
	private void add(String word, int recordId)	{
		if(this.invertedIndex.containsKey(word))	{ // key - word is already present
			if(this.invertedIndex.get(word).getInvertedIndexValues().containsKey(recordId))	{ //recordId is already present
				int currentFreq = this.invertedIndex.get(word).getInvertedIndexValues().get(recordId);
				currentFreq += 1;
				this.invertedIndex.get(word).getInvertedIndexValues().put(recordId, currentFreq);
			}
			else	{  //recordId is not present/// but word is present
				this.invertedIndex.get(word).getInvertedIndexValues().put(recordId, 1);
			}
		}
		else	{   // key - word is not present 
			AmazonWordDetails invertedIndexValue = new AmazonWordDetails();
			invertedIndexValue.getInvertedIndexValues().put(recordId, 1);
			this.invertedIndex.put(word, invertedIndexValue);
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//InvertedIndex ir = new InvertedIndex();
		//ir.getTextString("This Text is new .. whaat ??", 1);
		//ir.getTextString("this te'xt was new .. what ??", 2);
		//ir.getTextString("Th-is Text was old .. whaat ??", 3);
		//ir.getTextString("This Text is old .. whaat ??", 4);
		//ir.getTextString("This This is n:ew .. whaat ??", 5);

		//System.out.println("InvertedIndex: " + ir.getIndex().toString() );


	}

}
