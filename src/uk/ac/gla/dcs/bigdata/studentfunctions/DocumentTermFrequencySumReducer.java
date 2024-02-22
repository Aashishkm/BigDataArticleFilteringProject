package uk.ac.gla.dcs.bigdata.studentfunctions;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.ReduceFunction;

import uk.ac.gla.dcs.bigdata.studentstructures.QueryStructureList;

public class DocumentTermFrequencySumReducer implements ReduceFunction<QueryStructureList> {
	
	private static final long serialVersionUID = 504564822999825225L;
	
	
	Map<String, List<Integer>> q1 = new HashMap<>();
	Map<String, List<Integer>> q2 = new HashMap<>();

	@Override
	public QueryStructureList call(QueryStructureList v1, QueryStructureList v2) throws Exception {
		q1 = v1.getQueryTermFrequency();
		q2 = v2.getQueryTermFrequency();
		
		
		
		return null;
	}

}
