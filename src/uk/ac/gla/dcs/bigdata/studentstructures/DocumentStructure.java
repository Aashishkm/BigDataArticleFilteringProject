package uk.ac.gla.dcs.bigdata.studentstructures;

import java.io.Serializable;
import java.util.List;

import uk.ac.gla.dcs.bigdata.providedstructures.ContentItem;
import uk.ac.gla.dcs.bigdata.providedutilities.TextPreProcessor;

public class DocumentStructure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3674408047254280897L;
	
	String id; // unique article identifier
	//String article_url; // url pointing to the online article

	List<ContentItem> contents; // the contents of the article body

	List<String> tokenizedDocument; //content after it has been modified (tokenized + concatenated) 
	

	
	public DocumentStructure() {}
	
	public DocumentStructure(String id, List<ContentItem> contents, List<String> tokenizedDocument) {
		super();
		this.id = id;
	
		this.contents = contents;
		
		this.tokenizedDocument = tokenizedDocument; 

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	

	public List<ContentItem> getContents() {
		return contents;
	}

	public void setContents(List<ContentItem> contents) {
		this.contents = contents;
	}
	
	public List<String> getTokenizedDocument() {
		return tokenizedDocument;
	}

	public void setTokenizedDocument(List<String> tokenizedDocument) {
		this.tokenizedDocument = tokenizedDocument;
	}



}
