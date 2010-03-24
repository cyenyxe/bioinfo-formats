package org.bioinfo.formats.io.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bioinfo.commons.io.TextFileReader;
import org.bioinfo.formats.core.feature.Sequence;
import org.bioinfo.formats.io.exception.FileFormatException;

public class FastaReader extends SequenceReader {
	
	private TextFileReader fileReader;
	
	private static final String seqIdChar = ">";
	
	private String lastLineReaded = null;
	
	private String fastaFileName = null;
	
	public FastaReader (String fileName) throws FileNotFoundException{
		this.fileReader = new TextFileReader(fileName);
		this.fastaFileName = fileName;
	}

	@Override
	public void close() throws IOException {
		this.fileReader.close();

	}

	@Override
	public List<Sequence> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sequence> grep(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sequence next() throws FileFormatException {
		Sequence sequence = null;
		try {
			// If no previous sequences have been readed, 
			// we read the first(s) line(s)
			String idLine = null;
			if (this.lastLineReaded!=null){
				idLine = this.lastLineReaded;
			}else{
				// (1)TODO: Quizas haya que leer varias lineas hasta llegar a la primera secuencia
				idLine = this.fileReader.readLine();
			}
			
			// Leemos el ID y Descripcion
			// TODO: quizás la comprobación es innecesaria. Depende de (1)
			String id = null;
			String desc = null;
			if (idLine.startsWith(FastaReader.seqIdChar)){
				// TODO: Leer ID y descripcion si existe
			}else{
				throw new FileFormatException("Incorrect sequence ID: "+idLine);
			}
			
			// Leemos la sencuencia:
			String seq = null;
			String line = this.fileReader.readLine();
			while (!line.startsWith(FastaReader.seqIdChar)){
				// TODO: Probar rendimiento de StringBuffer(StringBuilder) y String
				seq = seq + line;				
			}
			this.lastLineReaded = line;
			
			// creamos la secuencia a partir de los atributos que hemos obtenido
			sequence = new Sequence(id,desc,seq);
			
		}catch (IOException ex){
			throw new FileFormatException(ex);
		}
		return sequence;
	}

	@Override
	public int size() {
		try {
			TextFileReader reader = new TextFileReader(this.fastaFileName);
			//List ids = reader.
		}catch (){
			
		}
		return 0;
	}

}
