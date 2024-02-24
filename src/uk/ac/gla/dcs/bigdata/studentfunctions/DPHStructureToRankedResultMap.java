package uk.ac.gla.dcs.bigdata.studentfunctions;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;

import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;
import uk.ac.gla.dcs.bigdata.providedstructures.RankedResult;
import uk.ac.gla.dcs.bigdata.studentstructures.DPHStructure;


public class DPHStructureToRankedResultMap implements MapFunction<DPHStructure, RankedResult> {

	/**
	  Output ranked result of articles based on the score for a given query 
	 */
	private static final long serialVersionUID = 2472120668545383514L;
	Broadcast<Query> broadcastIndividualQuery; 

	public DPHStructureToRankedResultMap(Broadcast<Query> broadcastIndividualQuery) {
		this.broadcastIndividualQuery = broadcastIndividualQuery; 
		
	}

	@Override
	public RankedResult call(DPHStructure value) throws Exception {
		
		Query query = broadcastIndividualQuery.getValue(); 
		
		String id = value.getId(); //docid?
		NewsArticle article = value.getArticle(); //article
		double score = value.getDphScoreDict().get(query); //score

		RankedResult ranks = new RankedResult(id, article, score); 
		// TODO Auto-generated method stub
		return ranks;
	}
	

}
