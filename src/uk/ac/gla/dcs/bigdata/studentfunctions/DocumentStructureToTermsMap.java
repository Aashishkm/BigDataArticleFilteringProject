package uk.ac.gla.dcs.bigdata.studentfunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.MapFunction;


//import com.sun.tools.javac.util.List;

import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.QueryStructureList;

//Need to map to a list of frequencies for the upcoming reduce, we are wrapping it in the class queryStructureList 
public class DocumentStructureToTermsMap implements MapFunction<DocumentStructure, QueryStructureList >{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4817418873484219582L;
	Map<String, List<Integer>> queries = new HashMap<>();

	@Override
	public QueryStructureList call(DocumentStructure value) throws Exception {
		
		queries = value.getTermFrequencyDict(); 
		QueryStructureList queryList = new QueryStructureList(queries);
				
		return queryList;
	}


}
