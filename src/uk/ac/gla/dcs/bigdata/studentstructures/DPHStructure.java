package uk.ac.gla.dcs.bigdata.studentstructures;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;


//Our Structure for storing DPH score and article information
public class DPHStructure implements Serializable{
	/**
	 Structure for the DPH scorehaving the id, article, a score map with query and score.
	 */
	private static final long serialVersionUID = 4517819071539280730L;
	
	String id; // unique article identifier
	//String article_url; 
	// int dphScore; 
	NewsArticle article;
	Map<Query, Double> dphScoreDict; 
	// Query query; 
	

	public DPHStructure() {
	}
	
	
	public DPHStructure(String id, NewsArticle article, Map<Query, Double> dphScoreDict) {
		super();
		this.id = id;
		this.article = article; 
		this.dphScoreDict = dphScoreDict; 

	}


	public String getId() {
		return id;
	}


	

	public NewsArticle getArticle() {
		return article;
	}


	public void setId(String id) {
		this.id = id;
	}


	

	public void setArticle(NewsArticle article) {
		this.article = article;
	}


	public Map<Query, Double> getDphScoreDict() {
		return dphScoreDict;
	}


	public void setDphScoreDict(Map<Query, Double> dphScoreDict) {
		this.dphScoreDict = dphScoreDict;
	}





	

}
