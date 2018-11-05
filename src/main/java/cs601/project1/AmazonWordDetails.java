package cs601.project1;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * AmazonWordDetails class is for storing details of a particular word
 * - so every unique word will have its own AmazonWordDetails object
 * @author anuragjha
 *
 */
public class AmazonWordDetails {


	private HashMap<Integer, Integer> invertedIndexValues;
	private LinkedHashMap<Integer, Integer> sortedInvertedIndexValues;
	private boolean isSorted;

	/**
	 * constructor for AmazonWordDetails class
	 */
	public AmazonWordDetails()	{
		invertedIndexValues = new HashMap<Integer, Integer>();
		isSorted = false;
	}


	/**
	 * getter for 
	 * @return the invertedIndexValues
	 */
	public Map<Integer, Integer> getInvertedIndexValues() {
		return invertedIndexValues;
		//return createSortedOutput();
	}


	/**
	 * createSortedOutput is a public method calls SortOutput method
	 * @return
	 */
	public Map<Integer, Integer> createSortedOutput()	{
		return this.SortOutput();
	}

	/**
	 * SortOutput method returns a sorted Linked Hashmap of recordIds and Count
	 * @param wordDetails
	 * @return sortedOutput
	 */
	public Map<Integer, Integer> SortOutput()	{

		if(!this.isSorted)	{ //if invertedIndex values are not sorted

			LinkedList<Map.Entry<Integer,Integer>> sortedInvertedIndexValueList = 
					new LinkedList<Map.Entry<Integer,Integer>>(this.invertedIndexValues.entrySet());

			Collections.sort(sortedInvertedIndexValueList, new Comparator<Map.Entry<Integer,Integer>>() {
				public int compare(Map.Entry<Integer,Integer> r1,Map.Entry<Integer,Integer> r2){
					return r2.getValue().compareTo(r1.getValue());
				}
			});

			this.invertedIndexValues.clear(); //emptying the previous unsorted entries
			sortedInvertedIndexValues = new LinkedHashMap<Integer, Integer>();
			for(Map.Entry<Integer, Integer> thisRecord : sortedInvertedIndexValueList)	{
				sortedInvertedIndexValues.put(thisRecord.getKey(), thisRecord.getValue());
			}
			//invertedIndexValues.
			this.isSorted = true;
			return this.sortedInvertedIndexValues;

		}
		else	{ //if invertedIndexValues sorted

			return this.sortedInvertedIndexValues;

		}

	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
