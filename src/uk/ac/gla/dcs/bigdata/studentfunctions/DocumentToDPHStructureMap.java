package uk.ac.gla.dcs.bigdata.studentfunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;

import uk.ac.gla.dcs.bigdata.providedstructures.ContentItem;
import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;
import uk.ac.gla.dcs.bigdata.providedutilities.DPHScorer;
import uk.ac.gla.dcs.bigdata.providedutilities.TextPreProcessor;
import uk.ac.gla.dcs.bigdata.studentstructures.DPHStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.TermFrequencyDictStructure;

public class DocumentToDPHStructureMap implements MapFunction<DocumentStructure, DPHStructure>{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9045059317226215060L;

	private transient DPHScorer scorer;
	
	Broadcast<List<Query>> broadcastQueries;
	Broadcast<Double> broadcastAverageDocumentLength;
	Broadcast<TermFrequencyDictStructure> broadcastTotalTermFrequency;
	
	public DocumentToDPHStructureMap(Broadcast<List<Query>> broadcastQueries,Broadcast<Double> broadcastAverageDocumentLength, Broadcast<TermFrequencyDictStructure> broadcastTotalTermFrequency) {
		this.broadcastQueries = broadcastQueries; 
		this.broadcastAverageDocumentLength = broadcastAverageDocumentLength; 
		this.broadcastTotalTermFrequency = broadcastTotalTermFrequency; 	
	}
	
	
	
	@Override
	public DPHStructure call(DocumentStructure value) throws Exception {
	
		if (scorer==null) scorer = new DPHScorer();
		
		String id = value.getId();
		NewsArticle article = value.getArticle(); 
		
		List<Query> queries = broadcastQueries.getValue(); 
		
		//for (int i = 0; i < broadcastQueries.size(); )
		
		
		
	
		
		//DPHStructure dphStruct = new DPHStructure(id, null, null, article); 
		
		return null;
	}

}
