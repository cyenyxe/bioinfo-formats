package org.bioinfo.formats.io.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bioinfo.formats.io.exception.FileFormatException;

public abstract class FileFormatReader<T> {

	protected File file;
	
	public abstract T next() throws FileFormatException;
	
	public abstract List<T> getAll();
	
	public abstract List<T> grep(String filter);
	
	public abstract int size();
	
	public abstract void close() throws IOException;
	
}
