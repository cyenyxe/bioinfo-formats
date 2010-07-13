package org.bioinfo.formats.exception;

public class FileFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileFormatException(String msg) {
		super(msg);
	}
	
	public FileFormatException(Exception e) {
		super(e.toString());
		this.setStackTrace(e.getStackTrace());
	}
	
	public FileFormatException(String msg, Exception e) {
		super(msg);
		this.setStackTrace(e.getStackTrace());
	}
	
}
