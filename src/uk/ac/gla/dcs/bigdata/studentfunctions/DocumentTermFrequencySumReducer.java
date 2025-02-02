package uk.ac.gla.dcs.bigdata.studentfunctions;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.ReduceFunction;

import uk.ac.gla.dcs.bigdata.studentstructures.TermFrequencyDictStructure;

//Reducer that sums the term frequency data across all documents for each Query 
//This is maintained through our custom Dicitonary structure (TermFrequencyDictStructure)
public class DocumentTermFrequencySumReducer implements ReduceFunction<TermFrequencyDictStructure> {
	
	private static final long serialVersionUID = 504564822999825225L;
	
	
	Map<String, List<Integer>> q1 = new HashMap<>();
	Map<String, List<Integer>> q2 = new HashMap<>();
	Map<String, List<Integer>> returnTermFrequencyList = new HashMap<>();
	List<Integer> q1WithinQueryTerms = new ArrayList<>();
	List<Integer> q2WithinQueryTerms = new ArrayList<>();
	List<Integer> returnWithinQueryTerms = new ArrayList<>();
	

	@Override
	public TermFrequencyDictStructure call(TermFrequencyDictStructure v1, TermFrequencyDictStructure v2) throws Exception {
		q1 = v1.getQueryTermDict();
		q2 = v2.getQueryTermDict();
		
		for (String key: q1.keySet()) { 
			q1WithinQueryTerms = q1.get(key); 
			q2WithinQueryTerms = q2.get(key);
			
			
			for (int i = 0; i < q1WithinQueryTerms.size(); i++) {

				int sum = (q1WithinQueryTerms.get(i) + q2WithinQueryTerms.get(i)); 
		
		        returnWithinQueryTerms.add(sum); //adding the summed term frequency to our new list
				
			}	


			returnTermFrequencyList.put(key,returnWithinQueryTerms); //adding the summed term list to our new dictionary
	
			returnWithinQueryTerms = new ArrayList<>(); 
					
		}
		
		TermFrequencyDictStructure retTermFrequencySUM = new TermFrequencyDictStructure(returnTermFrequencyList); //wrapping our term sum dictionary in a class
	
		return retTermFrequencySUM;
	} 

}
