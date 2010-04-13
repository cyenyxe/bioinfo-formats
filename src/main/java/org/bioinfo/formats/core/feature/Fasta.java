package org.bioinfo.formats.core.feature;


/**
 * @author parce
 *
 */
public class Fasta {
	
	/** Sequence ID */
	protected String id;
	
	/** Sequence description */
	protected String description;
		
	/** Sequence */
	protected String sequence;
	
	private static final String SEQ_ID_CHAR = ">";
	
	protected static final int SEQ_OUTPUT_MAX_LENGTH = 60;

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
	
	public int size(){
		return this.sequence.length();
	}

	@Override
	public String toString() {
		StringBuilder sb =  new StringBuilder(this.SEQ_ID_CHAR + this.id);
		sb.append(" " + this.description + "\n");
		// Split and append the sequence in lines with a maximum size of SEQ_OUTPUT_MAX_LENGTH
		int n = 0;
		while (this.size() > ((n+1)*this.SEQ_OUTPUT_MAX_LENGTH)) {
			sb.append((this.sequence.substring(n * this.SEQ_OUTPUT_MAX_LENGTH, (n+1) * this.SEQ_OUTPUT_MAX_LENGTH)) + "\n");
			n ++;
		}
		sb.append(this.sequence.substring(n * this.SEQ_OUTPUT_MAX_LENGTH));	
		
		return (sb.toString());
	}


}
