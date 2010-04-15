package org.bioinfo.formats.core.feature;

public class FastaQ extends Fasta {

	/** Sequence quality */
	private String quality;
	
	/** Vector contanining integer qualities */
	private int[] qualityIntVector;
	
	/** Average quality of the sequence */
	private double averageQuality;
	
	/** Minimun sequence quality */
	private int minimumQuality;
	
	/** Maximum sequence quality */
	private int maximumQuality;	
	
	/** FastaQ Format */
	private int format;
	
	/** Constant representing SANGER FORMAT */
	public static final int SANGER_FORMAT = 0;
	
	/** Constant representing ILLUMINA FORMAT */
	public static final int ILLUMINA_FORMAT = 1;
	
	/** Constant representing SOLEXA FORMAT */
	public static final int SOLEXA_FORMAT = 2;	
	
	private static final char[] SCALE_FIRST_CHAR = {'!','@',';'};
	
	private static final int[] SCALE_FIRST_INT = {0, 0, -5};
	
	private static final String SEQ_ID_CHAR = "@";
	
	private static final String QUALITY_ID_CHAR = "+";	
	
	public FastaQ(String id, String description, String sequence, String quality) {
		this(id, description, sequence, quality, FastaQ.SANGER_FORMAT);
	}
	
	public FastaQ(String id, String description, String sequence, String quality, int format) {
		super(id, description, sequence);		
		this.quality = quality;		
		this.format = format;
		this.convertQuality();
	}	

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
		this.convertQuality();
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
	
	public int[] getQualityIntVector(){
		return this.qualityIntVector;
	}
	
	public String toString(){
		StringBuilder sb =  new StringBuilder(this.SEQ_ID_CHAR + this.id);
		sb.append(" " + this.description + "\n");
		
		// Split and append the sequence in lines with a maximum size of SEQ_OUTPUT_MAX_LENGTH
		int n = 0;
		while (this.size() > ((n+1)*this.SEQ_OUTPUT_MAX_LENGTH)) {
			sb.append((this.sequence.substring(n * this.SEQ_OUTPUT_MAX_LENGTH, (n+1) * this.SEQ_OUTPUT_MAX_LENGTH)) + "\n");
			n ++;
		}
		sb.append(this.sequence.substring(n * this.SEQ_OUTPUT_MAX_LENGTH) + "\n");			

		// Split and append the quality in lines with a maximum size of SEQ_OUTPUT_MAX_LENGTH
		sb.append(this.QUALITY_ID_CHAR + "\n");
		n = 0;
		while (this.size() > ((n+1)*this.SEQ_OUTPUT_MAX_LENGTH)) {
			sb.append((this.quality.substring(n * this.SEQ_OUTPUT_MAX_LENGTH, (n+1) * this.SEQ_OUTPUT_MAX_LENGTH)) + "\n");
			n ++;
		}		
		sb.append(this.quality.substring(n * this.SEQ_OUTPUT_MAX_LENGTH));	

		return (sb.toString());		
	}
	
	private void convertQuality(){
		int total = 0;
		
		this.maximumQuality = Integer.MIN_VALUE;
		this.minimumQuality = Integer.MAX_VALUE;
		// TODO: comentar
		qualityIntVector = new int[this.quality.length()];
		for (int i=0; i<this.quality.length(); i++){
			char c = this.quality.charAt(i);
			qualityIntVector[i] = c - this.SCALE_FIRST_CHAR[this.format] + this.SCALE_FIRST_INT[this.format];
			total += this.qualityIntVector[i];

			this.maximumQuality = Math.max(this.qualityIntVector[i], this.maximumQuality);
			this.minimumQuality = Math.min(this.qualityIntVector[i], this.minimumQuality);
		}
		this.averageQuality = (double)total / this.quality.length();
	}

}
