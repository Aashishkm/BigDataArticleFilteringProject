package uk.ac.gla.dcs.bigdata.studentfunctions;

import java.util.Collections;
import java.util.List;

import org.apache.spark.api.java.function.MapFunction;
//import org.apache.spark.sql.Row;
import org.eclipse.jetty.server.Authentication.User;

import uk.ac.gla.dcs.bigdata.providedstructures.ContentItem;
import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
//import uk.ac.gla.dcs.bigdata.providedstructures.Query;
import uk.ac.gla.dcs.bigdata.providedutilities.TextPreProcessor;
import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;

public class DocumentFormatterMap implements MapFunction<NewsArticle,DocumentStructure> {
	
	private static final long serialVersionUID = 6475166483071609772L;

	private transient TextPreProcessor processor;
	
	@Override
	public DocumentStructure call(NewsArticle value) throws Exception {
	
		if (processor==null) processor = new TextPreProcessor();
		
		String id = value.getId();
		String title = value.getTitle(); 
		List<ContentItem> contents = value.getContents(); 
		
		List<String> tokenizedDocument; 
		
		List<String> tokenizedTitle = processor.process(title);
		
		//if (contents)
		

		
		//short[] queryTermCounts = new short[queryTerms.size()];
		//for (int i =0; i<queryTerms.size(); i++) queryTermCounts[i] = (short)1;
		
		 List<User> emptyList = Collections.emptyList();
		
	
		
		DocumentStructure document = new DocumentStructure(); 
		return document;
	}
	

}
