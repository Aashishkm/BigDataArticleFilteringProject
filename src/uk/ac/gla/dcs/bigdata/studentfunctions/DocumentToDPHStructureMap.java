package uk.ac.gla.dcs.bigdata.studentfunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.broadcast.Broadcast;

import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;
import uk.ac.gla.dcs.bigdata.providedutilities.DPHScorer;

import uk.ac.gla.dcs.bigdata.studentstructures.DPHStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.TermFrequencyDictStructure;

public class DocumentToDPHStructureMap implements MapFunction<DocumentStructure, DPHStructure>{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9045059317226215060L;


	Broadcast<List<Query>> broadcastQueries;
	Broadcast<Double> broadcastAverageDocumentLength;
	Broadcast<TermFrequencyDictStructure> broadcastTotalTermFrequency;
	Broadcast<Long> broadcastTotalDocsInCorpus;
	
	public DocumentToDPHStructureMap(Broadcast<List<Query>> broadcastQueries,Broadcast<Double> broadcastAverageDocumentLength, Broadcast<TermFrequencyDictStructure> broadcastTotalTermFrequency, Broadcast<Long> broadcastTotalDocsInCorpus) {
		this.broadcastQueries = broadcastQueries; 
		this.broadcastAverageDocumentLength = broadcastAverageDocumentLength; 
		this.broadcastTotalTermFrequency = broadcastTotalTermFrequency;
		this.broadcastTotalDocsInCorpus = broadcastTotalDocsInCorpus; 
	}
	
	
	
	@Override
	public DPHStructure call(DocumentStructure value) throws Exception {
	
		
		String id = value.getId();
		NewsArticle article = value.getArticle(); 
		List<Query> queries = broadcastQueries.getValue();
		Double averageDocumentLengthInCorpus = broadcastAverageDocumentLength.getValue(); //param 4
		Long totalDocsInCorpus = broadcastTotalDocsInCorpus.getValue(); //param 5
		int currentDocumentLength = value.getDocumentLength(); //param 3
		//have to pass in the specific query terms in the dictionary
		TermFrequencyDictStructure totalTermFrequency = broadcastTotalTermFrequency.getValue(); 
		Map<String, List<Integer>> specificTermFrequencyDict = value.getTermFrequencyDict(); 
		
		//return map with scores based on queries 
		Map<Query, Double> dphScoreDict = new HashMap<>();
		List<Double> averagingList = new ArrayList<>();
		List<Integer> allTermFrequencies = new ArrayList<>(); 
		List<Integer> allTotalTermFrequencies = new ArrayList<>(); 
		Map<String, List<Integer>> totalTermFrequencyDict = new HashMap<>(); 
		
		
		for (int i = 0; i < queries.size(); i++) {
			List<String> terms = queries.get(i).getQueryTerms(); 
			String queryKeys = queries.get(i).getOriginalQuery(); //because the keys for our dictionarys are the original string
			
			for (int j = 0; j < terms.size(); j++) {
				
		
				
				allTermFrequencies = specificTermFrequencyDict.get(queryKeys); //getting list of terms
				
				short termFrequencyCurrentDocument = allTermFrequencies.get(j).shortValue(); //param1 
				
				totalTermFrequencyDict = totalTermFrequency.getQueryTermDict(); //unwrapping class 
				allTotalTermFrequencies = totalTermFrequencyDict.get(queryKeys); //getting list of terms
				
				int totalTermFrequencyInCorpus = allTotalTermFrequencies.get(j); //param2
				
				
				if (termFrequencyCurrentDocument == 0) {
					Double m = 0.0; 
					averagingList.add(m); 
					break; 
				}
				//System.out.println("Term Frequency for current: " + termFrequencyCurrentDocument);	
				//System.out.println("total term Frequency: " + totalTermFrequencyInCorpus);
				//System.out.println("current document legnth: " + currentDocumentLength);
				//System.out.println("Average document length: " + averageDocumentLengthInCorpus);
				//System.out.println("Total Documents in Corpus: " + totalDocsInCorpus);
				
				Double preScore = DPHScorer.getDPHScore(termFrequencyCurrentDocument, totalTermFrequencyInCorpus, currentDocumentLength, averageDocumentLengthInCorpus, totalDocsInCorpus);
				//System.out.println("Total corresponding term score is: " + preScore);
				averagingList.add(preScore); 
			}
			
			double adder = 0; 
			
			for (int p = 0; p < averagingList.size(); p++) {
				adder = adder + averagingList.get(p); 			
			}
			
			Double score = adder/averagingList.size();
			
			dphScoreDict.put(queries.get(i), score);
			
			
			
			averagingList = new ArrayList<>(); 
			
		}
		
		/*
		for (Double values : dphScoreDict.values()) {
			System.out.println("Total corresponding term score is: " + values);
		}
		*/
		
		DPHStructure dphStruct = new DPHStructure(id, article, dphScoreDict); 
		
		return dphStruct;
	}

}
