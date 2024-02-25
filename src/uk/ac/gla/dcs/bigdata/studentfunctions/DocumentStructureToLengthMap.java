package uk.ac.gla.dcs.bigdata.studentfunctions;

import org.apache.spark.api.java.function.MapFunction;


//Maps our main Document Structure structure to just a length Vector Used in our DocumentLengthSum Reducer
import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;

public class DocumentStructureToLengthMap implements MapFunction<DocumentStructure, Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7053269391712990313L;
	
	@Override
	public Integer call(DocumentStructure value) throws Exception {
		return value.getDocumentLength();
	}


}
