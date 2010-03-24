package org.bioinfo.formats.io.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bioinfo.commons.io.BeanReader;
import org.bioinfo.commons.io.utils.IOUtils;
import org.bioinfo.formats.core.feature.Bed;
import org.bioinfo.formats.io.exception.FileFormatException;

public class BedReader extends AbstractFormatReader<Bed> {

	private BeanReader<Bed> beanReader;

	public BedReader(String filename) throws IOException, SecurityException, NoSuchMethodException {
		this(new File(filename));	
	}

	public BedReader(File file) throws IOException, SecurityException, NoSuchMethodException {
		super(file);
		beanReader = new BeanReader<Bed>(file, Bed.class);
	}

	@Override
	public void close() throws IOException {
		beanReader.close();
	}

	@Override
	public List<Bed> readAll() {
		return null;
	}

	@Override
	public List<Bed> readAll(String filter) {
		return null;
	}

	@Override
	public Bed read() throws FileFormatException {
		try {
			return beanReader.next();
		} catch (Exception e) {
			throw new FileFormatException(e);
		}
	}

	@Override
	public int size() throws IOException {
		return IOUtils.countLines(file);
	}

	@Override
	public Bed read(String regexFilter) throws FileFormatException {
		return null;
	}


}
