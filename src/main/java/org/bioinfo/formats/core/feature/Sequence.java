package org.bioinfo.formats.core.feature;


/**
 * @author parce
 *
 */
public class Sequence {
	
	/** Sequence ID */
	private String id;
	
	/** Sequence description */
	private String description;
	
	/** Sequence */
	private String seq;

	public Sequence(String id, String description, String seq) {
		this.id = id;
		this.description = description;
		this.seq = seq;
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
		return seq;
	}

	/**
	 * @param seq
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "Sequence [id=" + id + ", description=" + description + ", seq="
				+ seq + "]";
	}


}
