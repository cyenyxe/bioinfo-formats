package org.bioinfo.formats.io.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bioinfo.commons.io.BeanReader;
import org.bioinfo.commons.io.utils.FileUtils;
import org.bioinfo.formats.core.feature.Bed;
import org.bioinfo.formats.io.exception.FileFormatException;

public class BedReader extends FileFormatReader<Bed> {

	private BeanReader<Bed> beanReader;

	public BedReader(String filename) throws IOException, SecurityException, NoSuchMethodException {
		this(new File(filename));	
	}

	public BedReader(File file) throws IOException, SecurityException, NoSuchMethodException {
		FileUtils.checkFile(file);
		this.file = file;
		beanReader = new BeanReader<Bed>(file, Bed.class);
	}

	@Override
	public void close() throws IOException {
		beanReader.close();
	}

	@Override
	public List<Bed> getAll() {
		return null;
	}

	@Override
	public List<Bed> grep(String filter) {
		return null;
	}

	@Override
	public Bed next() throws FileFormatException {
		try {
			return beanReader.next();
		} catch (Exception e) {
			throw new FileFormatException(e);
		}
	}

	@Override
	public int size() {
		return 0;
	}


}
