package uk.ac.gla.dcs.bigdata.studentfunctions;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.ReduceFunction;

import uk.ac.gla.dcs.bigdata.studentstructures.TermFrequencySumDict;

public class DocumentTermFrequencySumReducer implements ReduceFunction<TermFrequencySumDict> {
	
	private static final long serialVersionUID = 504564822999825225L;
	
	
	Map<String, List<Integer>> q1 = new HashMap<>();
	Map<String, List<Integer>> q2 = new HashMap<>();
	Map<String, List<Integer>> returnTermFrequencyList = new HashMap<>();
	List<Integer> q1WithinQueryTerms = new ArrayList<>();
	List<Integer> q2WithinQueryTerms = new ArrayList<>();
	List<Integer> returnWithinQueryTerms = new ArrayList<>();
	

	@Override
	public TermFrequencySumDict call(TermFrequencySumDict v1, TermFrequencySumDict v2) throws Exception {
		q1 = v1.getQueryTermDict();
		q2 = v2.getQueryTermDict();
		
		for (String key: q1.keySet()) { 
			q1WithinQueryTerms = q1.get(key); 
			q2WithinQueryTerms = q2.get(key);
			
			for (int i = 0; i < q1WithinQueryTerms.size(); i++) {
				System.out.println("size of q1 is: " + q1WithinQueryTerms.size());
				System.out.println("size of q2 is: " + q2WithinQueryTerms.size());
				System.out.println("variable: " + i);

				// int sum = (q1WithinQueryTerms.get(i) + q2WithinQueryTerms.get(i));
		        //System.out.println("the sume is: " + sum); 
		        //returnWithinQueryTerms.add(sum);
				
			}	
			
			returnTermFrequencyList.put(key,returnWithinQueryTerms); 
			
		}
		
		TermFrequencySumDict retTermFrequencySUM = new TermFrequencySumDict(returnTermFrequencyList); 
	
		return retTermFrequencySUM;
	} 

}
