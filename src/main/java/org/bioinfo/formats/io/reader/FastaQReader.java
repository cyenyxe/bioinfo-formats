package org.bioinfo.formats.io.reader;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bioinfo.commons.io.TextFileReader;
import org.bioinfo.formats.core.feature.FastaQ;
import org.bioinfo.formats.io.exception.FileFormatException;

public class FastaQReader extends AbstractFormatReader<FastaQ> {
	
	private TextFileReader fileReader;
	
	private static final String SEQ_ID_CHAR = "@";
	
	private static final String QUALITY_ID_CHAR = "+";
	
	private int format;
	
	public FastaQReader(String fileName, int format) throws IOException {
		this(new File(fileName), format);
	}	
	
	public FastaQReader(String fileName) throws IOException{
		this(new File(fileName));
	}

	public FastaQReader(File file) throws IOException {
		this(file, FastaQ.SANGER_FORMAT);
	}	
	
	public FastaQReader(File file, int format) throws IOException {
		super(file);
		this.fileReader = new TextFileReader(file.getAbsolutePath());
		this.format = format;
	}		

	@Override
	public void close() throws IOException {
		this.fileReader.close();
	}
	
	@Override
	public List<FastaQ> readAll() throws FileFormatException {
		List<FastaQ> fastaList = new LinkedList<FastaQ>();

		FastaQ fasta;
		while ((fasta = this.read()) != null){
			fastaList.add(fasta);
		}

		return fastaList;		
	}
	
	@Override
	public List<FastaQ> readAll(String regexFilter) throws FileFormatException {
		List<FastaQ> fastaList = new LinkedList<FastaQ>();

		FastaQ fasta;
		while ((fasta = this.read(regexFilter)) != null){
			fastaList.add(fasta);
		}

		return fastaList;
	}		
	
	public FastaQ read() throws FileFormatException{
		FastaQ fasta = null;	

		try {
			// Read Id Line. If it's null, the end of file has been reached
			String idLine = this.readIdLine();
			if (idLine != null) {
				// Obtain Id and Desc from Id Line
				String id = idLine.split("\\s")[0].substring(1);
				String desc = idLine.substring(id.length()+1);
	
				// Read Sequence
				StringBuilder sequenceBuilder =  new StringBuilder();
				int numSequenceLines = this.readSequenceLines(sequenceBuilder);		
				String sequence = sequenceBuilder.toString().trim();
				
				// Read Quality
				StringBuilder qualityBuilder = this.readQualityLines(numSequenceLines);
				String quality = qualityBuilder.toString().trim();
				
				// Check that sequence and quality sizes are equal
				this.checkQualitySize(id, sequence, quality);
	
				// Build Fasta object
				fasta = new FastaQ(id, desc.trim(), sequence, quality, this.format);
			}
		}catch (IOException ex){
			throw new FileFormatException(ex);
		}

		return fasta;		
	}
	
	public int size() throws IOException, FileFormatException{
		int size = 0;
		while (this.read() != null){
			size ++;
		}
		return size;
	}
	
	@Override
	public FastaQ read(String regexFilter) throws FileFormatException {
		FastaQ seq = this.read();
		boolean found = false;
		while (!found && seq != null){
			if (seq.getId().matches(regexFilter)){
				found = true;
			} else {
				seq = this.read();
			}
		}
		return seq;
	}
	
	private String readIdLine() throws  FileFormatException,IOException {
		String idLine;

		// TODO: Comprobar si hay lineas de basura antes de la primera secuencia,
		//		 en lugar de lanzar una excepcion directamente
		idLine = this.fileReader.readLine();
		if ((idLine != null) && !idLine.startsWith(FastaQReader.SEQ_ID_CHAR)){
			throw new FileFormatException("Incorrect ID Line: "+idLine);				
		}

		return idLine;
	}	
	
	private int readSequenceLines(StringBuilder sequenceBuilder) throws FileFormatException, IOException {
		int numSequenceLines = 0;
		// read the sequence string
		String line = this.fileReader.readLine();
		while (line != null && !line.startsWith(FastaQReader.QUALITY_ID_CHAR)){
			// check the sequence format and throws a FileFormatException if it's wrong 
			checkSequence(line);
			sequenceBuilder.append(line);
			numSequenceLines++;
			line = this.fileReader.readLine();
		}

		return numSequenceLines;
	}	
	
	
	private StringBuilder readQualityLines (int numSequenceLines) throws IOException, FileFormatException{
		StringBuilder qualityBuilder = new StringBuilder();
		
		String line;
		int numLinesRead = 1;		
		while ((numLinesRead <= numSequenceLines) && (line = this.fileReader.readLine()) != null){
			// check the sequence format and throws a FileFormatException if it's wrong 
			checkQuality(line);
			qualityBuilder.append(line);
			numLinesRead++;
			//line = this.fileReader.readLine();
		}		
		
		return qualityBuilder;
	}

	private void checkSequence(String sequence) throws FileFormatException {
		// TODO: Por ahora no hacemos comprobacion alguna y nos creemos que la secuencia viene bien
	}	
	
	private void checkQuality(String sequence) throws FileFormatException {
		// TODO: Por ahora no hacemos comprobacion alguna y nos creemos que la secuencia viene bien
	}		
	
	/**
	 * Check that the sequence and quality strings have the same length
	 * @param id - FastQ id
	 * @param sequence - FastaQ sequence string
	 * @param quality - FastQ quality string
	 * @throws FileFormatException - If the sequence and quality strings have different lengths
	 */
	private void checkQualitySize(String id, String sequence, String quality) throws FileFormatException {
		if (sequence.length() != quality.length()){
			throw new FileFormatException("Quality and Sequence lenghts are different in Fasta " + id);			
		}
	}
	
}
