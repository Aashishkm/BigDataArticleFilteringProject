package uk.ac.gla.dcs.bigdata.studentstructures;

import java.util.List;
import java.util.Map;

public class QueryStructureList {
	Map<String, List<Integer>> queryTermFrequency;

	public QueryStructureList() {}
	
	public QueryStructureList(Map<String, List<Integer>> queryTermFrequency) {
		super();
		this.queryTermFrequency = queryTermFrequency;
	}

	public Map<String, List<Integer>> getQueryTermFrequency() {
		return queryTermFrequency;
	}

	public void setQueryTermFrequency(Map<String, List<Integer>> queryTermFrequency) {
		this.queryTermFrequency = queryTermFrequency;
	}
	
	
	
}


