package org.bioinfo.formats.core.feature.io;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bioinfo.commons.io.BeanReader;
import org.bioinfo.commons.io.utils.IOUtils;
import org.bioinfo.formats.core.feature.Gff;
import org.bioinfo.formats.io.exception.FileFormatException;
import org.bioinfo.formats.io.reader.AbstractFormatReader;

public class GffReader extends AbstractFormatReader<Gff> {

	private BeanReader<Gff> beanReader;
	
	public GffReader(String filename) throws IOException, SecurityException, NoSuchMethodException {
		this(new File(filename));
	}
	
	public GffReader(File file) throws IOException, SecurityException, NoSuchMethodException {
		super(file);
		beanReader = new BeanReader<Gff>(file, Gff.class);
	}
	
	@Override
	public Gff read() throws FileFormatException {
		try {
			return beanReader.next();
		} catch (Exception e) {
			throw new FileFormatException(e);
		} 
	}

	@Override
	public Gff read(String regexFilter) throws FileFormatException {
		return null;
	}

	@Override
	public List<Gff> readAll() throws FileFormatException {
		try {
			return beanReader.getAll();
		} catch (Exception e) {
			throw new FileFormatException(e);
		}
	}

	@Override
	public List<Gff> readAll(String regexFilter) throws FileFormatException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() throws IOException, FileFormatException {
		return IOUtils.countLines(file);
	}

	@Override
	public void close() throws IOException {
		beanReader.close();
	}
	
}
