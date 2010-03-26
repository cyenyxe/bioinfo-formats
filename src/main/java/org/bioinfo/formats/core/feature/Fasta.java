package org.bioinfo.formats.core.feature;


/**
 * @author parce
 *
 */
public class Fasta {
	
	/** Sequence ID */
	private String id;
	
	/** Sequence description */
	private String description;
	
	/** Sequence */
	private String sequence;

	public Fasta(String id, String description, String sequence) {
		this.id = id;
		this.description = description;
		this.sequence = sequence;
	}

	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return
	 */
	public String getSeq() {
		return sequence;
	}

	/**
	 * @param seq
	 */
	public void setSeq(String seq) {
		this.sequence = seq;
	}

	@Override
	public String toString() {
		StringBuilder sb =  new StringBuilder("Fasta Id=" + this.id);
		sb.append("\nDescription=" + this.description);
		sb.append("\nSequence=" + this.sequence);
		return (sb.toString());
	}


}
