package uk.ac.gla.dcs.bigdata.studentstructures;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.spark.broadcast.Broadcast;

import uk.ac.gla.dcs.bigdata.providedstructures.ContentItem;
import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedutilities.TextPreProcessor;

public class DocumentStructure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3674408047254280897L;
	
	String id; // unique article identifier
	//String article_url; // url pointing to the online article
	List<String> tokenizedDocument; //content after it has been modified (tokenized + concatenated) 	
	int documentLength;
	NewsArticle article; 
	Map<String, List<Integer>> termFrequencyDict; 
	
	

	
	public DocumentStructure() {}
	
	
	public DocumentStructure(String id, List<String> tokenizedDocument, int documentLength, Map<String, List<Integer>> termFrequencyDict, NewsArticle article) {
		super();
		this.id = id;
		this.article = article; 
		this.tokenizedDocument = tokenizedDocument; 
		this.documentLength = documentLength; 
		this.termFrequencyDict = termFrequencyDict; 

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getDocumentLength() {
		return documentLength;
	}

	public void setDocumentLength(int documentLength) {
		this.documentLength = documentLength; 
	}
	
	public List<String> getTokenizedDocument() {
		return tokenizedDocument;
	}

	public void setTokenizedDocument(List<String> tokenizedDocument) {
		this.tokenizedDocument = tokenizedDocument;
	}

	public Map<String, List<Integer>> getTermFrequencyDict() {
		return termFrequencyDict;
	}

	public void setTermFrequencyDict(Map<String, List<Integer>> termFrequencyDict) {
		this.termFrequencyDict = termFrequencyDict;
	}

	public NewsArticle getArticle() {
		return article;
	}

	public void setArticle(NewsArticle article) {
		this.article = article;
	}
  


}
