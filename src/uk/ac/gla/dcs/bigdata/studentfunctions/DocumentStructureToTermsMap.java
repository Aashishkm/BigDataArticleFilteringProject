package uk.ac.gla.dcs.bigdata.studentfunctions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.MapFunction;


//import com.sun.tools.javac.util.List;

import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.TermFrequencyDictStructure;

//Maps DocumentStructure to a Dictionary of Query and term frequencies 
//Will be used for calculation of respective termFrequenciesAcrossDocuments in our DocumentTermFrequencySumReducer
public class DocumentStructureToTermsMap implements MapFunction<DocumentStructure, TermFrequencyDictStructure >{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4817418873484219582L;
	Map<String, List<Integer>> queries = new HashMap<>();

	@Override
	public TermFrequencyDictStructure call(DocumentStructure value) throws Exception {
		
		queries = value.getTermFrequencyDict(); 
		TermFrequencyDictStructure queryList = new TermFrequencyDictStructure(queries);
				
		return queryList;
	}


}
