package org.bioinfo.formats.io.reader;

import static org.junit.Assert.*;

import java.util.List;

import org.bioinfo.commons.io.TextFileWriter;
import org.bioinfo.commons.utils.ArrayUtils;
import org.bioinfo.formats.core.feature.FastQ;
import org.bioinfo.formats.core.feature.Fasta;
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
	
	@Ignore
	@Test
	public void testQualityLengthRelationship() {
		try {
			// Obtain the maximum length
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/pruebaformatoincorrecto.fq");			

			FastQ fq;
			int maxLength = 0;
			while((fq = fqr.read()) != null) {
				maxLength = Math.max(maxLength, fq.size());				
			}
			System.out.println("Maximum Sequence Length: " + maxLength);
			fqr.close();
			
			double[][] qualitiesPositionedArray = new double[maxLength][2]; 
			fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/pruebaformatoincorrecto.fq");				
			while((fq = fqr.read()) != null) {
				int[] qv = fq.getQualityScoresArray();
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
			FastQ fq;
			int n = 0;
			long totalQuality = 0;
			double maxAverageQuality = 0;
			double minAverageQuality = 93;
			int maxQuality = 0;
			int minQuality = 93;
			int minLength = Integer.MAX_VALUE;
			int maxLength = 0;
			while((fq = fqr.read()) != null) {
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
			
			FastQ fq;
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
			List<FastQ> FastaQList = fqr.readAll();
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
			List<FastQ>FastaQList = fqr.readAll(regExp);
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
	public void escribeCalidadesAFichero(){
		try {
			long ini = System.currentTimeMillis();
			FastaQReader fqr = new FastaQReader("/mnt/commons/test/biodata/fasta/SRR006041.filt.fastq");
			TextFileWriter fw = new TextFileWriter("/mnt/commons/test/biodata/fasta/longitudes_SRR006041.txt");
			
			FastQ fq;
			int n = 0;
			int length;
			while((fq = fqr.read()) != null) {
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
	
	@Ignore
	@Test
	public void testSangerFastQFormatConversions(){
		System.out.println ("Transforming SANGER Fasta to different formats");
		System.out.println ("==============================================");		
		// Sanger Format Fasta
		FastQ fasta = new FastQ("FormatsFastQTest",
								  "Format Transformations Test FastQ",
								  "ATTGCTGAC",
								  "!&+05:?DI",
								  FastQ.SANGER_FORMAT);
		System.out.println("Input Fasta, SANGER:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		String sangerQualityString = fasta.getQuality();
		
		// Fasta converted to Illumina Format
		fasta.changeFormat(FastQ.ILLUMINA_FORMAT);
		System.out.println("Illumina Converted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		// Fasta reconverted to Sanger Format
		fasta.changeFormat(FastQ.SANGER_FORMAT);
		System.out.println("Sanger Reconverted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");	
		
		assert(sangerQualityString.equals(fasta.getQuality()));
		
		fasta.changeFormat(FastQ.SOLEXA_FORMAT);
		System.out.println("Solexa Converted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		fasta.changeFormat(FastQ.SANGER_FORMAT);
		System.out.println("Sanger Reconverted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");		
		
	}
	
	@Ignore
	@Test
	public void testIlluminaFastQFormatConversions(){
		System.out.println ("Transforming Illumina Fasta to different formats");
		System.out.println ("================================================");		
		// Illumina Format Fasta
		FastQ fasta = new FastQ("FormatsFastQTest",
								  "Format Transformations Test FastQ",
								  "ATTGCTGAC",
								  "@EJOTY^ch",
								  FastQ.ILLUMINA_FORMAT);
		System.out.println("Input Fasta, ILLUMINA:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		String illuminaQualityString = fasta.getQuality();
		
		// Fasta converted to Sanger Format
		fasta.changeFormat(FastQ.SANGER_FORMAT);
		System.out.println("Sanger Converted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		// Fasta reconverted to Illumina Format
		fasta.changeFormat(FastQ.ILLUMINA_FORMAT);
		System.out.println("ILLUMINA Reconverted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");	
		
		assert(illuminaQualityString.equals(fasta.getQuality()));
		
		// Fasta converted to Solexa Format
		fasta.changeFormat(FastQ.SOLEXA_FORMAT);
		System.out.println("Solexa Converted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		// Fasta reconverted to Illumina Format
		fasta.changeFormat(FastQ.ILLUMINA_FORMAT);
		System.out.println("ILLUMINA Reconverted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");		
	}	
	
	@Ignore
	@Test
	public void testSolexaFastQFormatConversions(){
		System.out.println ("Transforming Solexa Fasta to different formats");
		System.out.println ("================================================");		
		// Solexa Format Fasta
		FastQ fasta = new FastQ("FormatsFastQTest",
								  "Format Transformations Test FastQ",
								  "CATTGCTGAC",
								  ";@EJOTY^ch",
								  FastQ.SOLEXA_FORMAT);
		System.out.println("Input Fasta, Solexa:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		String illuminaQualityString = fasta.getQuality();
		
		// Fasta converted to Illumina Format
		fasta.changeFormat(FastQ.ILLUMINA_FORMAT);
		System.out.println("Illumina Converted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		// Fasta reconverted to Solexa Format
		fasta.changeFormat(FastQ.SOLEXA_FORMAT);
		System.out.println("Solexa Reconverted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");	
		
		assert(illuminaQualityString.equals(fasta.getQuality()));
		
		// Fasta converted to Sanger Format
		fasta.changeFormat(FastQ.SANGER_FORMAT);
		System.out.println("Sanger Converted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		// Fasta reconverted to Solexa Format
		fasta.changeFormat(FastQ.SOLEXA_FORMAT);
		System.out.println("Solexa Reconverted Fasta:");
		System.out.println(fasta);
		System.out.println("Integer Qualities: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");		
	}		

	@Ignore
	@Test
	public void testSolexaToPhredScoresConversions(){
		System.out.println ("Transforming Solexa to Phred");
		System.out.println ("============================");		
		// Solexa Format Fasta
		FastQ fasta = new FastQ("ScoresFastQTest",
								  "Scores Transformations Test FastQ",
								  "CATTGCTGACTTGCA",
								  ";<=>?@ABCDEFGHI",
								  FastQ.SOLEXA_FORMAT);
		System.out.println("Input Fasta, Solexa Scores:");
		System.out.println(fasta);
		System.out.println("Integer Scores: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		// Fasta converted to Illumina Format
		fasta.changeFormat(FastQ.ILLUMINA_FORMAT);
		System.out.println("Illumina Converted Fasta, Phred Scores:");
		System.out.println(fasta);
		System.out.println("Integer Scores: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
	}
	
	@Ignore
	@Test
	public void testPhredToSolexaScoresConversions(){
		System.out.println ("Transforming Phred to Solexa");
		System.out.println ("============================");		
		// Illumina Format Fasta
		FastQ fasta = new FastQ("ScoresFastQTest",
								  "Scores Transformations Test FastQ",
								  "CATTGCTGAC",
								  "@ABCDEFGHI",
								  FastQ.SOLEXA_FORMAT);
		System.out.println("Input Fasta, Illumina Format, Phred Scores:");
		System.out.println(fasta);
		System.out.println("Integer Scores: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
		
		// Fasta converted to Illumina Format
		fasta.changeFormat(FastQ.ILLUMINA_FORMAT);
		System.out.println("Solexa Converted Fasta, Solexa Scores:");
		System.out.println(fasta);
		System.out.println("Integer Scores: ");
		System.out.println(ArrayUtils.toString(fasta.getQualityScoresArray())+"\n");
	}	
	
	@Test
	public void trimTest() {
		// Input fasta
		FastQ fasta = new FastQ("FastqTrimTest", "ltrim and rtrim fastq test", "CTGTACGTCGTAGTCGTAGC", "BBBBFFGGHHTBBB@@@AAB");
		System.out.println("Input Fastq: ");
		System.out.println(fasta.toString());
		System.out.println("Size: " + fasta.size());
		// lTrim
		fasta.lTrim(6);
		System.out.println("L trimmed Fastq: ");
		System.out.println(fasta);
		System.out.println("Size: " + fasta.size());
		assert(fasta.getSeq().equals("GTCGTAGTCGTAGC"));
		assert(fasta.getQuality().equals("GGHHTBBB@@@AAB"));		
		// rTrim
		fasta.rTrim(4);
		System.out.println("R trimmed Fastq: ");
		System.out.println(fasta);
		System.out.println("Size: " + fasta.size());
		assert(fasta.getSeq().equals("GTCGTAGTCG"));	
		assert(fasta.getQuality().equals("GGHHTBBB@@"));			
		// lTrim again
		fasta.lTrim(5);
		System.out.println("L trimmed again Fastq: ");
		System.out.println(fasta);
		System.out.println("Size: " + fasta.size());
		assert(fasta.getSeq().equals("AGTCG"));		
		assert(fasta.getQuality().equals("BBB@@"));		
	}	
	
}
