package cs601.project3;

public class HelloWorld {
	
	public static void main(String[] args) {
		String queryTerm = "super+man";
		String[] terms = queryTerm.split("\\+");
		for(String term : terms) {
			System.out.println("term: " + term);
		}
		
	}

}
