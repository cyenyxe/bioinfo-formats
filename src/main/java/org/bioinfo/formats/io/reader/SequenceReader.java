/**
 * 
 */
package org.bioinfo.formats.io.reader;

import java.io.IOException;
import java.util.List;

import org.bioinfo.formats.core.feature.Sequence;
import org.bioinfo.formats.io.exception.FileFormatException;

/**
 * @author parce
 *
 */
public abstract class SequenceReader extends FileFormatReader<Sequence> {
	
	public abstract Sequence next() throws FileFormatException;
	
	public abstract List<Sequence> getAll();
	
	public abstract List<Sequence> grep(String filter);
	
	public abstract int size();
	
	public abstract void close() throws IOException;

}
