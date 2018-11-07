package searchPack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * AmazonWordDetails class is for storing details of a particular word
 * - so every unique word will have its own AmazonWordDetails object
 * @author anuragjha
 *
 */
public class AmazonWordDetails {


	private HashMap<AmazonReviews, Integer> invertedIndexValues;
	private LinkedHashMap<AmazonReviews, Integer> sortedInvertedIndexValues;
	
	private boolean isSorted;

	/**
	 * constructor for AmazonWordDetails class
	 */
	public AmazonWordDetails()	{
		invertedIndexValues = new HashMap<AmazonReviews, Integer>();
		isSorted = false;
	}


	/**
	 * getter for 
	 * @return the invertedIndexValues
	 */
	public Map<AmazonReviews, Integer> getInvertedIndexValues() {
		return invertedIndexValues;
		//return createSortedOutput();
	}
	
	/**
	 * getter for 
	 * @return the invertedIndexValues
	 */
	public ArrayList<AmazonReviews> getSortedInvertedIndexValues() {
		return (new ArrayList<AmazonReviews>(sortedInvertedIndexValues.keySet()));
		
	}
	


	/**
	 * createSortedOutput is a public method calls SortOutput method
	 * @return
	 */
	public Map<AmazonReviews, Integer> createSortedOutput()	{
		return this.SortOutput();
	}

	/**
	 * SortOutput method returns a sorted Linked Hashmap of recordIds and Count
	 * @param wordDetails
	 * @return sortedOutput
	 */
	public Map<AmazonReviews, Integer> SortOutput()	{

		if(!this.isSorted)	{ //if invertedIndex values are not sorted

			LinkedList<Map.Entry<AmazonReviews,Integer>> sortedInvertedIndexValueList = 
					new LinkedList<Map.Entry<AmazonReviews,Integer>>(this.invertedIndexValues.entrySet());

			Collections.sort(sortedInvertedIndexValueList, new Comparator<Map.Entry<AmazonReviews,Integer>>() {
				public int compare(Map.Entry<AmazonReviews,Integer> r1,Map.Entry<AmazonReviews,Integer> r2){
					return r2.getValue().compareTo(r1.getValue());
				}
			});

			this.invertedIndexValues.clear(); //emptying the previous unsorted entries
			sortedInvertedIndexValues = new LinkedHashMap<AmazonReviews, Integer>();
			for(Map.Entry<AmazonReviews, Integer> thisRecord : sortedInvertedIndexValueList)	{
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
