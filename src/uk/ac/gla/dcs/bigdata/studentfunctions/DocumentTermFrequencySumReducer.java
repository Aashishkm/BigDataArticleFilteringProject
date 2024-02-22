package uk.ac.gla.dcs.bigdata.studentfunctions;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.ReduceFunction;

import uk.ac.gla.dcs.bigdata.studentstructures.TermFrequencyDictStructure;

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
		
		//System.out.println("Dict of q1 is: " + q1);
		//System.out.println("Dict of q2 is: " + q2);
		Boolean check = false; 
		
		for (String key: q1.keySet()) { 
			q1WithinQueryTerms = q1.get(key); 
			q2WithinQueryTerms = q2.get(key);
			
			
			for (int i = 0; i < q1WithinQueryTerms.size(); i++) {
				
				//System.out.println("size of q1 is: " + q1WithinQueryTerms.size());
				//System.out.println("size of q2 is: " + q2WithinQueryTerms.size());
				//System.out.println("variable: " + i);
                //if (q2.)
				int sum = (q1WithinQueryTerms.get(i) + q2WithinQueryTerms.get(i));
				
				//if ((q2WithinQueryTerms.get(i) == null) || (q1WithinQueryTerms.get(i) == null)) {
				//	sum = 0; 
				//}
				//System.out.println("sum variable: " + sum);
				//System.out.println("sum of q2 is: ");
				//System.out.println("sum of q2 is: " + q2WithinQueryTerms.get(i));
		        //System.out.println("sum of q1 is: " + q1WithinQueryTerms.get(i));
		        returnWithinQueryTerms.add(sum);
				
			}	
			//System.out.println("Within Query Terms: " + returnWithinQueryTerms);
			//returnWithinQueryTerms.add(1); 
			//System.out.println("sum of q2 is: " + returnWithinQueryTerms);
			returnTermFrequencyList.put(key,returnWithinQueryTerms);
			//System.out.println(returnTermFrequencyList);
			returnWithinQueryTerms = new ArrayList<>(); 
					
		}
		
		TermFrequencyDictStructure retTermFrequencySUM = new TermFrequencyDictStructure(returnTermFrequencyList); 
	
		return retTermFrequencySUM;
	} 

}
