package uk.ac.gla.dcs.bigdata.studentstructures;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TermFrequencyDictStructure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3105210442318667494L;
	Map<String, List<Integer>> queryTermDict;
	

	public TermFrequencyDictStructure() {}
	
	public TermFrequencyDictStructure(Map<String, List<Integer>> queryTermDict) {
		super();
		this.queryTermDict = queryTermDict;
	}

	public Map<String, List<Integer>> getQueryTermDict() {
		return queryTermDict;
	}

	public void setQueryTermDict(Map<String, List<Integer>> queryTermDict) {
		this.queryTermDict = queryTermDict;
	}
	
	
	
}


