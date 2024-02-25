package uk.ac.gla.dcs.bigdata.studentfunctions;

import org.apache.spark.api.java.function.ReduceFunction;

//Sums all document lengths to be used for average document length calculation
public class DocumentLengthSumReducer implements ReduceFunction<Integer>{ 
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3687217776558121793L;

	@Override
	public Integer call(Integer v1, Integer v2) throws Exception {
		return v1+v2;
	}

}
