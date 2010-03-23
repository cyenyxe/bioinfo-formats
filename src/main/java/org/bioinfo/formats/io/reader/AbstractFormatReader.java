package org.bioinfo.formats.io.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bioinfo.commons.io.utils.FileUtils;
import org.bioinfo.formats.io.exception.FileFormatException;

public abstract class AbstractFormatReader<T> {

	protected File file;
	
	protected AbstractFormatReader(File f) throws IOException {
		FileUtils.checkFile(file);
		this.file = f;
	}
	
	public abstract T read() throws FileFormatException;
	
	public abstract T read(String regexFilter) throws FileFormatException;
	
	public abstract List<T> readAll();
	
	public abstract List<T> readAll(String regexFilter);
	
	public abstract int size() throws IOException;
	
	public abstract void close() throws IOException;
	
}
