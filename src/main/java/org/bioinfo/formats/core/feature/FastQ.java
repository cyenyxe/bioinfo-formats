package org.bioinfo.formats.core.feature;

import java.util.HashMap;
import java.util.Map;

public class FastQ extends Fasta {

	/** Sequence quality */
	private String quality;
	
	/** Vector contanining PHRED or Solexa (depending on the format) quality scores */
	private int[] qualityScoresArray;
	
	/** Average quality of the sequence */
	private double averageQuality;
	
	/** Minimun sequence quality */
	private int minimumQuality;
	
	/** Maximum sequence quality */
	private int maximumQuality;	
	
	/** FastQ Format */
	private int format;
	
	/** Constant representing SANGER FORMAT */
	public static final int SANGER_FORMAT = 0;
	
	/** Constant representing ILLUMINA FORMAT */
	public static final int ILLUMINA_FORMAT = 1;
	
	/** Constant representing SOLEXA FORMAT */
	public static final int SOLEXA_FORMAT = 2;	
	
	/** First char in the different quality scales */
	private static final char[] SCALE_OFFSET = {33, 64, 64};	
	
	/** Constant representing PHRED score type */
	private static final int PHRED_SCORE_TYPE = 0;
	
	/** Constant representing Solexa score type */	
	private static final int SOLEXA_SCORE_TYPE = 1;
	
	/** Score type corresponding to each scale */
	private static final int[] SCALE_SCORE = {FastQ.PHRED_SCORE_TYPE, FastQ.PHRED_SCORE_TYPE, FastQ.SOLEXA_SCORE_TYPE};
	
	/** Sequence ID Line first char */
	private static final String SEQ_ID_CHAR = "@";
	
	/** Quality ID line first char */
	private static final String QUALITY_ID_CHAR = "+";	
	
	/** Solexa to Phred Score Map */ 
	private static Map<Integer,Integer> solexaToPhredMap;
	
	/** Phred to Solexa Score Map */ 
	private static Map<Integer,Integer> phredToSolexaMap;
	
	static{
		// Solexa To Phred Score Map Initialization
		solexaToPhredMap = new HashMap<Integer,Integer>();
		solexaToPhredMap.put(-5, 1);
		solexaToPhredMap.put(-4, 1);
		solexaToPhredMap.put(-3, 2);
		solexaToPhredMap.put(-2, 2);
		solexaToPhredMap.put(-1, 3);
		solexaToPhredMap.put(0, 3);
		solexaToPhredMap.put(1, 4);
		solexaToPhredMap.put(2, 4);
		solexaToPhredMap.put(3, 5);
		solexaToPhredMap.put(4, 5);
		solexaToPhredMap.put(5, 6);	
		solexaToPhredMap.put(6, 7);
		solexaToPhredMap.put(7, 8);
		solexaToPhredMap.put(8, 9);
		solexaToPhredMap.put(9, 10);		

		// PHRED To Solexa Score Map Initialization
		phredToSolexaMap = new HashMap<Integer,Integer>();
		phredToSolexaMap.put(0, -5);
		phredToSolexaMap.put(1, -5);
		phredToSolexaMap.put(2, -2);
		phredToSolexaMap.put(3, 0);
		phredToSolexaMap.put(4, 2);
		phredToSolexaMap.put(5, 3);	
		phredToSolexaMap.put(6, 5);
		phredToSolexaMap.put(7, 6);
		phredToSolexaMap.put(8, 7);
		phredToSolexaMap.put(9, 8);		
	}
	
	public FastQ(String id, String description, String sequence, String quality) {
		this(id, description, sequence, quality, FastQ.SANGER_FORMAT);
	}
	
	public FastQ(String id, String description, String sequence, String quality, int format) {
		super(id, description, sequence);		
		this.format = format;
		this.setQuality(quality);
	}	

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
		this.obtainQualityScores();
	}
	
	public double getAverageQuality() {
		return this.averageQuality;
	}

	public void setAverageQuality(int averageQuality) {
		this.averageQuality = averageQuality;
	}	
	
	public int getMaximumQuality() {
		return this.maximumQuality;
	}

	public void setMaximumQuality(int maximumQuality) {
		this.maximumQuality = maximumQuality;
	}
	
	public int getMinimumQuality() {
		return this.minimumQuality;
	}

	public void setMinimumQuality(int minimumQuality) {
		this.minimumQuality = minimumQuality;
	}	
	
	public int[] getQualityScoresArray(){
		return this.qualityScoresArray;
	}
	
	public String toString(){
		StringBuilder sb =  new StringBuilder(FastQ.SEQ_ID_CHAR + this.id);
		sb.append(" " + this.description + "\n");
		
		// Split and append the sequence in lines with a maximum size of SEQ_OUTPUT_MAX_LENGTH
		int n = 0;
		while (this.size() > ((n+1)*Fasta.SEQ_OUTPUT_MAX_LENGTH)) {
			sb.append((this.sequence.substring(n * Fasta.SEQ_OUTPUT_MAX_LENGTH, (n+1) * Fasta.SEQ_OUTPUT_MAX_LENGTH)) + "\n");
			n ++;
		}
		sb.append(this.sequence.substring(n * Fasta.SEQ_OUTPUT_MAX_LENGTH) + "\n");			

		// Split and append the quality in lines with a maximum size of SEQ_OUTPUT_MAX_LENGTH
		sb.append(FastQ.QUALITY_ID_CHAR + "\n");
		n = 0;
		while (this.size() > ((n+1)*Fasta.SEQ_OUTPUT_MAX_LENGTH)) {
			sb.append((this.quality.substring(n * Fasta.SEQ_OUTPUT_MAX_LENGTH, (n+1) * Fasta.SEQ_OUTPUT_MAX_LENGTH)) + "\n");
			n ++;
		}		
		sb.append(this.quality.substring(n * Fasta.SEQ_OUTPUT_MAX_LENGTH));	

		return (sb.toString());		
	}
	
	/**
	 * This method obtain the quality scores corresponding to the quality char sequence, 
	 * depending on the sequence's format, and calculate sequence's average quality and 
	 * maximum and minimum individual quality scores 
	 */
	private void obtainQualityScores(){
		int total = 0;
		this.maximumQuality = Integer.MIN_VALUE;
		this.minimumQuality = Integer.MAX_VALUE;
		// quality int array initialization
		qualityScoresArray = new int[this.quality.length()];
		
		// Transform each character in the quality String into a integer, depending on 
		// the quality scale, and obtain the average, minimum and maximum values
		for (int i=0; i<this.quality.length(); i++){
			char c = this.quality.charAt(i);
			qualityScoresArray[i] = c - FastQ.SCALE_OFFSET[this.format];			
			total += this.qualityScoresArray[i];

			this.maximumQuality = Math.max(this.qualityScoresArray[i], this.maximumQuality);
			this.minimumQuality = Math.min(this.qualityScoresArray[i], this.minimumQuality);
		}
		this.averageQuality = (double)total / this.quality.length();
	}
	
	/**
	 * Change the format of the sequence, and recalculates the quality scores array
	 * @param format - New quality format
	 */
	public void changeFormat(int newFormat){
		if (this.format != newFormat){
			int oldFormat = this.format;
			// Transform the quality scores if the score scales are different 
			if (this.differentScoreScales(oldFormat, newFormat)){
				this.transformQualityScoresArray(oldFormat, newFormat);
			}
			// Transform the quality string 
			this.obtainQualityStringFromQualityScoresArray(newFormat);				
			this.format = newFormat;
		}
	}
	
	/**
	 * Transform the quality scores array to the new format scores scale
	 * @param newFormat - new format
	 */
	private void transformQualityScoresArray(int oldFormat, int newFormat) {
		// Score Map selection
		Map<Integer, Integer> scoreMap;
		if (FastQ.SCALE_SCORE[oldFormat] == FastQ.PHRED_SCORE_TYPE){
			scoreMap = FastQ.phredToSolexaMap;
		} else {
			scoreMap = FastQ.solexaToPhredMap;			
		}
		// Transform each quality score in the quality scores array
		for (int i=0; i<this.qualityScoresArray.length; i++) {
			if (qualityScoresArray[i] < 10) {
				qualityScoresArray[i] = scoreMap.get(qualityScoresArray[i]);			
			}
		}
	}
	
	/**
	 * Obtain the quality string related to a score array in the indicated format
	 * @param format - format
	 */
	private void obtainQualityStringFromQualityScoresArray(int format) {
		char[] qualityChars = new char[this.qualityScoresArray.length];
		// add the scale offset to each individual score and transform the result to a char
		for (int i=0; i<this.qualityScoresArray.length; i++){
			qualityChars[i] = (char)(this.qualityScoresArray[i] + FastQ.SCALE_OFFSET[format]);
		}
		this.quality = new String(qualityChars);
	}
	
	/**
	 * Check if the score scales associated to two formats are different
	 * @param format1 - first format
	 * @param format2 - second format 
	 * @return boolean - true if the score scales are different
	 */
	private boolean differentScoreScales(int format1, int format2){
		return (FastQ.SCALE_SCORE[format1] != FastQ.SCALE_SCORE[format2]);
	}

	/**
	 * Trim the sequence's tail, if it is longer than a determined size
	 * @param maxSize - Maximum size allowed
	 */
	public void trimSequenceTail (int maxSize){
		// Trim sequence and quality strings
		this.setSeq(this.sequence.substring(0, maxSize));
		this.setQuality(this.quality.substring(0, maxSize));
	}
	
	/**
	 * Returns the average quality of the last elements of the sequence
	 * @param numElements - Number of elements whose quality will be returned
	 * @return - Average quality of the last elements of the sequence
	 */
	public float getSequenceTailAverageQuality(int numElements){
		float quality = -1;
		if (this.size() >= numElements){
			// Sum the quality of the last 'n' elements of the sequence, 
			// and divide the result by 'n' to obtain the average value 
			int totalTailQuality = 0;
			for (int i=1; i <= numElements; i++){
				totalTailQuality += this.qualityScoresArray[this.size()-i];
			}
			quality = totalTailQuality / numElements;
		}
		return quality;
	}	
	
	/**
	 * Check if the given format is valid
	 * @param format - format to check
	 * @return boolean - true if the format is valid
	 */
	public static boolean validFormat(int format){
		return ( format == FastQ.SANGER_FORMAT || format == FastQ.SOLEXA_FORMAT || format == FastQ.ILLUMINA_FORMAT);
	}
	
}
