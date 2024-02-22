package uk.ac.gla.dcs.bigdata.studentstructures;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;



public class DPHStructure implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4517819071539280730L;
	
	String id; // unique article identifier
	//String article_url; // url pointing to the online article
	 //content after it has been modified (tokenized + concatenated) 	
	// int dphScore; 
	NewsArticle article;
	Map<Query, Integer> dphScoreDict; 
	// Query query; 
	

	public DPHStructure() {
		// TODO Auto-generated constructor stub
	}
	
	
	public DPHStructure(String id, NewsArticle article, Map<Query, Integer> dphScoreDict) {
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


	public Map<Query, Integer> getDphScoreDict() {
		return dphScoreDict;
	}


	public void setDphScoreDict(Map<Query, Integer> dphScoreDict) {
		this.dphScoreDict = dphScoreDict;
	}





	

}
