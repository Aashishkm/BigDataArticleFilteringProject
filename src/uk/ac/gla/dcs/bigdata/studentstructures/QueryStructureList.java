package uk.ac.gla.dcs.bigdata.studentstructures;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class QueryStructureList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3105210442318667494L;
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


