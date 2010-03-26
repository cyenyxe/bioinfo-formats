package org.bioinfo.formats.core.feature;

public class FastaQ extends Fasta {

	/** Sequence quality */
	private String quality;
	
	/** Vector contanining integer qualities */
	private int[] qualityInt;
	
	/** Average quality of the sequence */
	private double averageQuality;
	
	/** Minimun sequence quality */
	private int minimumQuality;
	
	/** Maximum sequence quality */
	private int maximumQuality;	
	
	/** FastaQ Format */
	private int format;
	
	/** Constant representing SANGER FORMAT */
	private static final int SANGER_FORMAT = 0;
	
	/** Constant representing ILLUMINA FORMAT */
	private static final int ILLUMINA_FORMAT = 1;
	
	/** Constant representing SOLEXA FORMAT */
	private static final int SOLEXA_FORMAT = 2;	
	
	private static final char[] scaleFirstChar = {'!','@',';'};
	
	private static final int[] scaleFirstInt = {0,0,-5};
	
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
	
	public String toString(){
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\nQuality="+this.quality);
		return sb.toString();
		// TODO: Mostrar tambien calidades
	}
	
	
	private void convertQuality(){
		int total = 0;
		// TODO: adaptar esta guarreria de maximos y minimos a los diferentes formatos
		this.maximumQuality = 0;
		this.minimumQuality = 93;
		qualityInt = new int[this.quality.length()];
		for (int i=0; i<this.quality.length(); i++){
			char c = this.quality.charAt(i);
			qualityInt[i] = c - this.scaleFirstChar[this.format] + this.scaleFirstInt[this.format];
			total += this.qualityInt[i];
//			if (this.qualityInt[i] > this.maximumQuality) {
//				this.maximumQuality = this.qualityInt[i];
//			}
			this.maximumQuality = Math.max(this.qualityInt[i], this.maximumQuality);
//			if (this.qualityInt[i] < this.minimumQuality) {
//				this.minimumQuality = this.qualityInt[i];
//			}
			this.minimumQuality = Math.min(this.qualityInt[i], this.minimumQuality);
		}
		this.averageQuality = (double)total / this.quality.length();
	}

}
