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
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/SRR006041.filt.fastq");
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
	
//	@Ignore
//	@Test
//	public void testQualityLengthRelationship() {
//		try {
//			FastaQReader fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
//			FastaQ fq;
//			int minLength = Integer.MAX_VALUE;
//			int maxLength = 0;
//			while((fq = fqr.read()) != null) {
//				minLength = Math.min(minLength, fq.size());
//				maxLength = Math.max(maxLength, fq.size());				
//			}
//			System.out.println("Minimum Sequence Length: " + minLength);
//			System.out.println("Maximum Sequence Length: " + maxLength);
//			fqr.close();
//			
//			double[][] arrayLongitudes = new double[maxLength-minLength+1][2]; 
//			fqr = new FastaQReader("/home/parce/fastas/SRR006041.filt.fastq");
//			while((fq = fqr.read()) != null) {
//				arrayLongitudes[fq.size()-minLength][0] += fq.getAverageQuality();
//				arrayLongitudes[fq.size()-minLength][1]++;
//			}
//			fqr.close();
//			
//			//TextFileWriter fw = new TextFileWriter("/home/parce/fastas/longitudes_SRR006041.txt");
//			for (int i=0;i<(maxLength-minLength);i++){
//				System.out.println(arrayLongitudes[i][0]/arrayLongitudes[i][1]);
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail(e.toString());
//		}
//		
//	}
	
	@Ignore
	@Test
	public void testQualityLengthRelationship() {
		try {
			// Obtain the maximum length
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/pruebaformatoincorrecto.fq");			

			FastaQ fq;
			int maxLength = 0;
			while((fq = fqr.read()) != null) {
				maxLength = Math.max(maxLength, fq.size());				
			}
			System.out.println("Maximum Sequence Length: " + maxLength);
			fqr.close();
			
			double[][] qualitiesPositionedArray = new double[maxLength][2]; 
			fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/pruebaformatoincorrecto.fq");				
			while((fq = fqr.read()) != null) {
				int[] qv = fq.getQualityIntVector();
				for (int i=0; i < qv.length; i++){
					qualitiesPositionedArray[i][0] += qv[i];
					qualitiesPositionedArray[i][1]++;					
				}
			}
			fqr.close();
			
			TextFileWriter fw = new TextFileWriter("/mnt/commons/test/biodata/fasta/CalidadesPorPosicion2.txt");
			for (int i=0;i<maxLength;i++){
				System.out.println(qualitiesPositionedArray[i][0]/qualitiesPositionedArray[i][1]);
				fw.writeLine("" + (qualitiesPositionedArray[i][0]/qualitiesPositionedArray[i][1]));
			}
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}	
	
	@Ignore
	@Test
	public void testRead() {
		try {
			long ini = System.currentTimeMillis();
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/SRR006041.filt.fastq");
			FastaQ fq;
			int n = 0;
			long totalQuality = 0;
			double maxAverageQuality = 0;
			double minAverageQuality = 93;
			int maxQuality = 0;
			int minQuality = 93;
			int minLength = Integer.MAX_VALUE;
			int maxLength = 0;
			while((fq = fqr.read()) != null) {
				//System.out.println(fq.toString());
				totalQuality += fq.getAverageQuality();
				maxAverageQuality = Math.max(maxAverageQuality, fq.getAverageQuality());
				minAverageQuality = Math.min(minAverageQuality, fq.getAverageQuality());	
				maxQuality = Math.max(maxQuality, fq.getMaximumQuality());
				minQuality = Math.min(minQuality, fq.getMinimumQuality());
				minLength = Math.min(minLength, fq.size());
				maxLength = Math.max(maxLength, fq.size());				
				n++;
			}
			long end = System.currentTimeMillis();
			System.out.println("FastaQReader.Read: " + n + " sequences read");
			System.out.println("Average Quality: " + (totalQuality / n));
			System.out.println("Maximum Average Quality: " + maxAverageQuality);
			System.out.println("Minimum Average Quality: " + minAverageQuality);	
			System.out.println("Absolute Maximum quality: " + maxQuality);
			System.out.println("Absolute Minimum quality: " + minQuality);	
			System.out.println("Minimum Sequence Length: " + minLength);
			System.out.println("Maximum Sequence Length: " + maxLength);				
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
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/SRR006041.filt.fastq");
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
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/SRR006041.filt.fastq");
			
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
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/SRR006041.filt.fastq");
			TextFileWriter fw = new TextFileWriter("/mnt/commons/test/biodata/fasta/longitudes_SRR006041.txt");
			
			FastaQ fq;
			int n = 0;
			//double averageQuality;
			int length;
			while((fq = fqr.read()) != null) {
				//System.out.println(fq.toString());
//				averageQuality = fq.getAverageQuality();
//				fw.writeLine(""+averageQuality);
				length = fq.size();
				fw.writeLine(""+length);
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
