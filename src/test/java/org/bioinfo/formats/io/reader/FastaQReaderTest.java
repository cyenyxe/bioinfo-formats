package org.bioinfo.formats.io.reader;

import static org.junit.Assert.*;

import java.util.List;

import org.bioinfo.commons.io.TextFileWriter;
import org.bioinfo.formats.core.feature.FastaQ;
import org.junit.Ignore;
import org.junit.Test;

public class FastaQReaderTest {

	@Ignore
	@Test
	public void testSize() {
		try {
			FastaQReader fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
			int size = fqr.size();
			System.out.println("FastaQReader.size: This file contains " + size + " sequences");
			fqr.close();
			assert (size == 10000);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Ignore
	@Test
	public void testClose() {
		try {
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/e_coli_10000snp.fq");
			fqr.close();
			System.out.println("FastaQReader.close: File closed");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	

	@Test
	public void testRead() {
		try {
			long ini = System.currentTimeMillis();
			FastaQReader fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/1000seqSanger.fq");
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/pruebaCalidades");
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/e_coli_10000snp.fq");	
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/e_coli_1000_1.fq");
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/e_coli_1000_2.fq");
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/e_coli_1000.fq");	
			FastaQ fq;
			int n = 0;
			long totalQuality = 0;
			double maxAverageQuality = 0;
			double minAverageQuality = 93;
			int maxQuality = 0;
			int minQuality = 93;
			while((fq = fqr.read()) != null) {
				//System.out.println(fq.toString());
				totalQuality += fq.getAverageQuality();
				if (fq.getAverageQuality() > maxAverageQuality) {
					maxAverageQuality = fq.getAverageQuality();
				}
				if (fq.getAverageQuality() < minAverageQuality) {
					minAverageQuality = fq.getAverageQuality();
				}		
				if (fq.getMaximumQuality() > maxQuality) {
					maxQuality = fq.getMaximumQuality();
				}
				if (fq.getMinimumQuality() < minQuality) {
					minQuality = fq.getMinimumQuality();
				}	
				n++;
			}
			long end = System.currentTimeMillis();
			System.out.println("FastaQReader.Read: " + n + " sequences read");
			System.out.println("Average Quality: " + (totalQuality / n));
			System.out.println("Maximum Average Quality: " + maxAverageQuality);
			System.out.println("Minimum Average Quality: " + minAverageQuality);	
			System.out.println("Absolute Maximum quality: " + maxQuality);
			System.out.println("Absolute Minimum quality: " + minQuality);			
			System.out.println("Time elapsed: " + ((end - ini)/ 1000) + " seconds");
			fqr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Ignore	
	@Test
	public void testReadString() {
		try {
			FastaQReader fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
			String regExp = "SRR006041.5[0-9]{1}";
			
			FastaQ fq;
			int n = 0;
			while((fq = fqr.read(regExp)) != null) {
				System.out.println(fq.toString());
				n++;
			}
			System.out.println("FastaQReader.ReadString: " + n + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fqr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Ignore
	@Test
	public void testReadAll() {
		try {
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/e_coli_10000snp.fq");
			List<FastaQ> FastaQList = fqr.readAll();
			System.out.println("FastaQReader.ReadAll: "+FastaQList.size()+" sequences read");
			fqr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Ignore
	@Test
	public void testReadAllString() {
		try {
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/e_coli_10000snp.fq");
			String regExp = "r199[0-9]";
			List<FastaQ>FastaQList = fqr.readAll(regExp);
			System.out.println("FastaQReader.ReadAllString: " + FastaQList.size() + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fqr.close();
			
			FastaQReader fqr2 = new FastaQReader("/mnt/commons/test/biodata/fasta/e_coli_10000snp.fq");
			regExp = "r1000[0-9]";
			FastaQList = fqr2.readAll(regExp);
			System.out.println("FastaQReader.ReadAllString: " + FastaQList.size() + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fqr2.close();
			
			FastaQReader fqr3 = new FastaQReader("/mnt/commons/test/biodata/fasta/e_coli_10000snp.fq");
			regExp = "r[0-9]+";
			FastaQList = fqr3.readAll(regExp);
			System.out.println("FastaQReader.ReadAllString: " + FastaQList.size() + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fqr3.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Ignore
	@Test
	public void testReadWithMinimalAverageQuality() {
		try {
			FastaQReader fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
			
			FastaQ fq;
			int minimum = 38;
			int n = 0;
			while((fq = fqr.readWithMinimalAverageQuality(minimum)) != null) {
				System.out.println(fq.toString());
				n++;
			}
			System.out.println("testReadWithMinimalAverageQuality: " + n + " sequences whose average quality is mayor or equal than " + minimum);
			fqr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}		
	}
	
	@Ignore
	@Test
	public void escribeCalidadesAFichero(){
		try {
			long ini = System.currentTimeMillis();
			FastaQReader fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
			TextFileWriter fw = new TextFileWriter("/home/parce/fastas/calidades_SRR006041.txt");
			//FastaQReader fqr = new FastaQReader("/home/parce/fastas/1000seqSanger.fq");
			FastaQ fq;
			int n = 0;
			double averageQuality;
			while((fq = fqr.read()) != null) {
				//System.out.println(fq.toString());
				averageQuality = fq.getAverageQuality();
				fw.writeLine(""+averageQuality);
				n++;
			}
			long end = System.currentTimeMillis();
			System.out.println("FastaQReader.Read: " + n + " sequences read");		
			System.out.println("Time elapsed: " + ((end - ini)/ 1000) + " seconds");
			fqr.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}

	}

}
