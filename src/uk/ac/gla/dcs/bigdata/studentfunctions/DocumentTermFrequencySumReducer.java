package uk.ac.gla.dcs.bigdata.studentfunctions;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.ReduceFunction;

import uk.ac.gla.dcs.bigdata.studentstructures.QueryStructureList;

public class DocumentTermFrequencySumReducer implements ReduceFunction<QueryStructureList> {
	
	private static final long serialVersionUID = 504564822999825225L;
	
	
	Map<String, List<Integer>> q1 = new HashMap<>();
	Map<String, List<Integer>> q2 = new HashMap<>();
	Map<String, List<Integer>> returnTermFrequencyList = new HashMap<>();
	List<Integer> q1WithinQueryTerms = new ArrayList<>();
	List<Integer> q2WithinQueryTerms = new ArrayList<>();
	List<Integer> returnWithinQueryTerms = new ArrayList<>();
	

	@Override
	public QueryStructureList call(QueryStructureList v1, QueryStructureList v2) throws Exception {
		q1 = v1.getQueryTermFrequency();
		q2 = v2.getQueryTermFrequency();
		
		for (String key: q1.keySet()) {
			q1WithinQueryTerms = q1.get(key); 
			q2WithinQueryTerms = q2.get(key);
			
			for (int i = 0; i < q1WithinQueryTerms.size(); i++) {
				
		        //returnWithinQueryTerms.add(q1WithinQueryTerms.get(0) + q2WithinQueryTerms.get(0));
				int j = 0; 
				returnWithinQueryTerms.add(j);
			}	

			returnTermFrequencyList.put(key,returnWithinQueryTerms); 
			
		}
		
		QueryStructureList retTermFrequencySUM = new QueryStructureList(returnTermFrequencyList); 
	
		return retTermFrequencySUM;
	}

}
