package uk.ac.gla.dcs.bigdata.studentfunctions;

import org.apache.spark.api.java.function.MapFunction;

//import com.sun.tools.javac.util.List;

import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;

public class DocumentStructureToTermsMap implements MapFunction<DocumentStructure, Integer >{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4817418873484219582L;

	@Override
	public Integer call(DocumentStructure value) throws Exception {
		return null;
	}


}
