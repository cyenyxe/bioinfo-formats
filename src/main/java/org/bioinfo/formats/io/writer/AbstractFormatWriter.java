package org.bioinfo.formats.io.writer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bioinfo.commons.io.utils.FileUtils;
import org.bioinfo.formats.io.exception.FileFormatException;

public abstract class AbstractFormatWriter<T> {
	
	protected File file;
	
	protected AbstractFormatWriter(File f) throws IOException {
		// TODO: ¿chequear si creamos un archivo nuevo o nos cepillamos uno o que?
		//FileUtils.checkFile(f);
		this.file = f;
	}	
	
	public abstract void write(T object) throws IOException;
	
	public abstract void writeAll(List<T> list) throws IOException;	
	
	public abstract void close() throws IOException;	
}
