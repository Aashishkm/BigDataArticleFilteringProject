package uk.ac.gla.dcs.bigdata.studentfunctions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.spark.api.java.function.MapFunction;
//import org.apache.spark.sql.Row;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;


import uk.ac.gla.dcs.bigdata.providedstructures.ContentItem;
import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;
//import uk.ac.gla.dcs.bigdata.providedstructures.Query;
import uk.ac.gla.dcs.bigdata.providedutilities.TextPreProcessor;
import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;


public class DocumentFormatterMap implements MapFunction<NewsArticle,DocumentStructure> {
	
	private static final long serialVersionUID = 6475166483071609772L;

	private transient TextPreProcessor processor;
	
	Broadcast<List<Query>> broadcastQueries;
	
	public DocumentFormatterMap(Broadcast<List<Query>> broadcastQueries) {
		this.broadcastQueries = broadcastQueries; 
		
	}
	
	
	
	@Override
	public DocumentStructure call(NewsArticle value) throws Exception {
	
		if (processor==null) processor = new TextPreProcessor();
		
		int documentLength = 0; 
		String id = value.getId();
		String title = value.getTitle(); 
		List<ContentItem> contents = value.getContents(); //retreiving our content,id and title from NewsArticle
		
		
		List<String> tokenizedDocument = null;
		List<String> tokenizedContent = null; 
		
		tokenizedDocument = processor.process(title); //tokenizing title 
	
		
		int paragraphCounter = 0; 
		
		for (int i = 0; i < contents.size(); i++) { //checking through ContentItem
			
			if (contents.get(i).getSubtype()!= null) {
			
				if (contents.get(i).getSubtype().equals("paragraph")) { //if ContentItem Equals paragraph
				
					tokenizedContent = processor.process(contents.get(i).getContent()); //tokenizing content
				
					tokenizedDocument.addAll(tokenizedContent); //adding tokenized paragraphs to our document
				
					paragraphCounter++; //incremenrt paragraphs counter
				}
			}
					
			if (paragraphCounter == 5) { //if we have 5 paragraphs we don't need anymore content 
				break; 
			}
				
		}
		//Calculate the term frequency for each term? 
		List<Query> queries = broadcastQueries.getValue(); 
		List<String> terms;  
		Map<String, List<Integer>> termFrequencyDict = new HashMap<>(); 
		List<Integer> termsList = new ArrayList<>(); 

		
		for (int i = 0; i < queries.size(); i++) {
			terms = queries.get(i).getQueryTerms(); 
			for (int j = 0; j < terms.size(); j++) {
				int termFrequency = Collections.frequency(tokenizedDocument, terms.get(j)); //using built in collections method 
				
				termsList.add(termFrequency); 
				
				
			}
			
			termFrequencyDict.put(queries.get(i).getOriginalQuery(), termsList);
			
			termsList = new ArrayList<>(); 

		}
		
		//System.out.println(termFrequencyDict);
		
		
		
		//Calculate the documentLength within this map 
		documentLength = tokenizedDocument.size(); 
		
		DocumentStructure document = new DocumentStructure(id, contents, tokenizedDocument, documentLength, termFrequencyDict); 
		
		return document;
	}
	

}
