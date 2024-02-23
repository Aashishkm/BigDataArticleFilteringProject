package uk.ac.gla.dcs.bigdata.apps;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


import uk.ac.gla.dcs.bigdata.providedfunctions.NewsFormaterMap;
import uk.ac.gla.dcs.bigdata.providedfunctions.QueryFormaterMap;
import uk.ac.gla.dcs.bigdata.providedstructures.DocumentRanking;
import uk.ac.gla.dcs.bigdata.providedstructures.NewsArticle;
import uk.ac.gla.dcs.bigdata.providedstructures.Query;
import uk.ac.gla.dcs.bigdata.providedstructures.RankedResult;
import uk.ac.gla.dcs.bigdata.studentfunctions.DPHStructureToRankedResultMap;
import uk.ac.gla.dcs.bigdata.studentfunctions.DocumentFormatterMap;
import uk.ac.gla.dcs.bigdata.studentfunctions.DocumentLengthSumReducer;
import uk.ac.gla.dcs.bigdata.studentfunctions.DocumentStructureToLengthMap;
import uk.ac.gla.dcs.bigdata.studentfunctions.DocumentStructureToTermsMap;
import uk.ac.gla.dcs.bigdata.studentfunctions.DocumentTermFrequencySumReducer;
import uk.ac.gla.dcs.bigdata.studentfunctions.DocumentToDPHStructureMap;
import uk.ac.gla.dcs.bigdata.studentstructures.DPHStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.DocumentStructure;
import uk.ac.gla.dcs.bigdata.studentstructures.TermFrequencyDictStructure;

/**
 * This is the main class where your Spark topology should be specified.
 * 
 * By default, running this class will execute the topology defined in the
 * rankDocuments() method in local mode, although this may be overriden by
 * the spark.master environment variable.
 * @author Richard
 *
 */
public class AssessedExercise {

	
	public static void main(String[] args) {
		
		File hadoopDIR = new File("resources/hadoop/"); // represent the hadoop directory as a Java file so we can get an absolute path for it
		System.setProperty("hadoop.home.dir", hadoopDIR.getAbsolutePath()); // set the JVM system property so that Spark finds it
		
		// The code submitted for the assessed exerise may be run in either local or remote modes
		// Configuration of this will be performed based on an environment variable
		String sparkMasterDef = System.getenv("spark.master");
		if (sparkMasterDef==null) sparkMasterDef = "local[2]"; // default is local mode with two executors
		
		String sparkSessionName = "BigDataAE"; // give the session a name
		
		// Create the Spark Configuration 
		SparkConf conf = new SparkConf()
				.setMaster(sparkMasterDef)
				.setAppName(sparkSessionName);
		
		// Create the spark session
		SparkSession spark = SparkSession
				  .builder()
				  .config(conf)
				  .getOrCreate();
	
		
		// Get the location of the input queries
		String queryFile = System.getenv("bigdata.queries");
		if (queryFile==null) queryFile = "data/queries.list"; // default is a sample with 3 queries
		
		// Get the location of the input news articles
		String newsFile = System.getenv("bigdata.news");
		if (newsFile==null) newsFile = "data/TREC_Washington_Post_collection.v3.example.json"; // default is a sample of 5000 news articles
		
		// Call the student's code
		List<DocumentRanking> results = rankDocuments(spark, queryFile, newsFile);
		
		// Close the spark session
		spark.close();
		
		// Check if the code returned any results
		if (results==null) System.err.println("Topology return no rankings, student code may not be implemented, skiping final write.");
		else {
			
			// We have set of output rankings, lets write to disk
			
			// Create a new folder 
			File outDirectory = new File("results/"+System.currentTimeMillis());
			if (!outDirectory.exists()) outDirectory.mkdir();
			
			// Write the ranking for each query as a new file
			for (DocumentRanking rankingForQuery : results) {
				rankingForQuery.write(outDirectory.getAbsolutePath());
			}
		}
		
		
	}
	
	
	
	public static List<DocumentRanking> rankDocuments(SparkSession spark, String queryFile, String newsFile) {
		
		// Load queries and news articles
		Dataset<Row> queriesjson = spark.read().text(queryFile);
		Dataset<Row> newsjson = spark.read().text(newsFile); // read in files as string rows, one row per article
		
		// Perform an initial conversion from Dataset<Row> to Query and NewsArticle Java objects
		Dataset<Query> queries = queriesjson.map(new QueryFormaterMap(), Encoders.bean(Query.class)); // this converts each row into a Query
		Dataset<NewsArticle> news = newsjson.map(new NewsFormaterMap(), Encoders.bean(NewsArticle.class)); // this converts each row into a NewsArticle
		

		//----------------------------------------------------------------
		// Your Spark Topology should be defined here
		//----------------------------------------------------------------

		//Collecting the queries to use as a broadcast variable throughout our program
		
		List<Query> queriesList = queries.collectAsList();
		Broadcast<List<Query>> broadcastQueries = JavaSparkContext.fromSparkContext(spark.sparkContext()).broadcast(queriesList);
		
		
    //Converted NewsArticle to DocumentStructure
        //Content and Title are tokenized and concatenated 
	//Calculation for each Documents Length is also in here 
	//Calculation for individual term frequencies is also in here 
		DocumentFormatterMap documentFormatterMap = new DocumentFormatterMap(broadcastQueries); 
		Dataset<DocumentStructure> tokenizedDocuments = news.map(documentFormatterMap, Encoders.bean(DocumentStructure.class)); 
		

		
		
	//Calculation for Average Document Length: 
	    //Extract the Document Length 
		Dataset<Integer> documentLengths = tokenizedDocuments.map(new DocumentStructureToLengthMap(), Encoders.INT());
		Integer documentLengthSUM = documentLengths.reduce(new DocumentLengthSumReducer());
	    double averageDocumentLength = (1.0*documentLengthSUM)/documentLengths.count(); 
		
		
		//System.out.println("The average document length is: " + averageDocumentLength);
			
	//Calculation for Sum of Term Frequencies for all documents			
		Encoder<TermFrequencyDictStructure> queryListEncoder = Encoders.bean(TermFrequencyDictStructure.class);
		Dataset<TermFrequencyDictStructure> documentTermFrequencies = tokenizedDocuments.map(new DocumentStructureToTermsMap(), queryListEncoder);
		TermFrequencyDictStructure termFrequenciesAcrossDocuments = documentTermFrequencies.reduce(new DocumentTermFrequencySumReducer());
		//System.out.println("The sum of a term frequency all the documents is : " + termFrequenciesAcrossDocuments.getQueryTermDict());
		
	//Calculation for nuumber of documents		
		List<NewsArticle> documentsList = news.collectAsList();
		
		Long numberofDocuments = (long)documentsList.size(); 
		
	//Broadcasting these so we can use them in our dph scorer (these are 2 of the parameters)
		Broadcast<Double> averageDocumentLengthInCorpus = JavaSparkContext.fromSparkContext(spark.sparkContext()).broadcast(averageDocumentLength);
		Broadcast<TermFrequencyDictStructure> totalTermFrequencyInCorpus = JavaSparkContext.fromSparkContext(spark.sparkContext()).broadcast(termFrequenciesAcrossDocuments);
		Broadcast<Long> totalDocsInCorpus = JavaSparkContext.fromSparkContext(spark.sparkContext()).broadcast(numberofDocuments);
		
		
	//Now doing the mapping to the structure with the dphScore: 
		DocumentToDPHStructureMap dphFormatterMap = new DocumentToDPHStructureMap(broadcastQueries, averageDocumentLengthInCorpus,totalTermFrequencyInCorpus, totalDocsInCorpus);
		Dataset<DPHStructure> dphPreProcessed = tokenizedDocuments.map(dphFormatterMap, Encoders.bean(DPHStructure.class)); 
		
		List<DPHStructure> dphData = dphPreProcessed.collectAsList(); 
		/*
		for (int i = 0; i < dphData.size(); i++) {
			System.out.println("The scores of the first documents queries are based on the documents is: " + dphData.get(0).getDphScoreDict());
		} */
		
		//prints scores for all document to verify we did it correctly
		
		Map<Query, Double> dphMap = new HashMap<>();
		for (int i = 0; i < dphData.size(); i++) {
			dphMap = dphData.get(i).getDphScoreDict(); 
			for (Double values : dphMap.values()) {
				System.out.println("Total corresponding term score for document " + i + " is: " + values);
			}
		} 
		
	//converting DPH structure to RankedResult 
		for (int j = 0; j < queriesList.size(); j++) {
			Broadcast<Query> broadcastIndividualQuery = JavaSparkContext.fromSparkContext(spark.sparkContext()).broadcast(queriesList.get(j));
			DPHStructureToRankedResultMap rankedResultMap = new DPHStructureToRankedResultMap(broadcastIndividualQuery);
			Dataset<RankedResult> rankedResultForQuery = dphPreProcessed.map(rankedResultMap, Encoders.bean(RankedResult.class)); 
			List<RankedResult> dphScorePerQueries = rankedResultForQuery.collectAsList(); 
			
			
		}
		
		//git stuff 
		
		return null; // replace this with the the list of DocumentRanking output by your topology
		
	}
	
	
}
