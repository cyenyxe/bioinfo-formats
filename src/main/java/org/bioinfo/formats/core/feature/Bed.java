package org.bioinfo.formats.core.feature;

public class Bed {

	private String chromosome;
	private int start;
	private int end;
	

//	public Bed(String chromosome, float start, float end) {
//		this(chromosome, Integer.parseInt(start), Integer.parseInt(end));
//	}
	
	public Bed(String chromosome, Integer start, Integer end) {
		this.chromosome = chromosome;
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return chromosome+"\t"+start+"\t"+end;
	}
	
	/**
	 * @param chromosome the chromosome to set
	 */
	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	/**
	 * @return the chromosome
	 */
	public String getChromosome() {
		return chromosome;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}
	
	
}
