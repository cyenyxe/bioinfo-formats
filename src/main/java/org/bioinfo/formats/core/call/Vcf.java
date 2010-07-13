package org.bioinfo.formats.core.call;

public class Vcf {

	private String chromosome;
	private int position;
	private String id;
	private String reference;
	private String alternate;
	private String quality;
	private String filter;
	private String info;
	private String format;
	
	/**
	 * @param chromosome
	 * @param position
	 * @param id
	 * @param reference
	 * @param alternate
	 * @param quality
	 * @param filter
	 * @param info
	 */
	public Vcf(String chromosome, Integer position, String id, String reference, String alternate, String quality, String filter, String info) {
		this.chromosome = chromosome;
		this.position = position;
		this.id = id;
		this.reference = reference;
		this.alternate = alternate;
		this.quality = quality;
		this.filter = filter;
		this.info = info;
	}
	
	/**
	 * @param chromosome
	 * @param position
	 * @param id
	 * @param reference
	 * @param alternate
	 * @param quality
	 * @param filter
	 * @param info
	 */
	public Vcf(String chromosome, Integer position, String id, String reference, String alternate, String quality, String filter, String info, String format) {
		this.chromosome = chromosome;
		this.position = position;
		this.id = id;
		this.reference = reference;
		this.alternate = alternate;
		this.quality = quality;
		this.filter = filter;
		this.info = info;
		this.format =  format;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(chromosome).append("\t");
		builder.append(position).append("\t");
		builder.append(id).append("\t");
		builder.append(reference).append("\t");
		builder.append(alternate).append("\t");
		builder.append(quality).append("\t");
		builder.append(filter).append("\t");
		builder.append(info);
		if(format != null) {
			builder.append("\t").append(format);
		}
		return builder.toString();
	}


	/**
	 * @return the chromosome
	 */
	public String getChromosome() {
		return chromosome;
	}
	/**
	 * @param chromosome the chromosome to set
	 */
	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the alternate
	 */
	public String getAlternate() {
		return alternate;
	}
	/**
	 * @param alternate the alternate to set
	 */
	public void setAlternate(String alternate) {
		this.alternate = alternate;
	}

	/**
	 * @return the quality
	 */
	public String getQuality() {
		return quality;
	}
	/**
	 * @param quality the quality to set
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

}
