package org.bioinfo.formats.io.reader;

import static org.junit.Assert.*;

import java.util.List;
import java.io.IOException;
import java.util.LinkedList;

import org.bioinfo.commons.io.TextFileReader;
import org.bioinfo.formats.core.feature.Fasta;
import org.junit.Ignore;
import org.junit.Test;

public class FastaReaderTest {

	@Ignore
	@Test
	public void testSize() {
		try {
			FastaReader fr = new FastaReader("/mnt/commons/test/biodata/example/sequences_exact_200000_100_20.fa");
			int size = fr.size();
			System.out.println("FastaReader.size: This file contains " + size + " sequences");
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Ignore
	@Test
	public void testClose() {
		try {
			FastaReader fr = new FastaReader("/home/parce/fastas/hg19.fa");
			fr.close();
			System.out.println("FastaReader.close: File closed");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Ignore
	@Test
	public void testRead() {
		try {
			//FastaReader fr = new FastaReader("/mnt/commons/test/biodata/example/e_coli_O157_H7.fna");
			FastaReader fr = new FastaReader("/mnt/commons/test/biodata/example/sequences_exact_200000_100_20.fa");
			//FastaReader fr = new FastaReader("/home/parce/fastas/hg19.fa");
			Fasta f;
			int n = 0;
			while((f = fr.read()) != null) {
				//System.out.println(f.toString());
				n++;
			}
			System.out.println("FastaReader.Read: " + n + " sequences read");
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Ignore
	@Test
	public void testReadString() {
		try {
			FastaReader fr = new FastaReader("/mnt/commons/test/biodata/example/sequences_exact_200000_100_20.fa");
			String regExp = "read_1999[0-9]{2}";
			
			Fasta f;
			int n = 0;
			while((f = fr.read(regExp)) != null) {
				//System.out.println(f.toString());
				n++;
			}
			System.out.println("FastaReader.ReadString: " + n + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Ignore
	@Test
	public void testReadAll() {
		try {
			//FastaReader fr = new FastaReader("/mnt/commons/test/biodata/example/e_coli_O157_H7.fna");
			FastaReader fr = new FastaReader("/mnt/commons/test/biodata/example/sequences_exact_200000_100_20.fa");
			//FastaReader fr = new FastaReader("/home/parce/fastas/hg19.fa");
			List<Fasta> fastaList = fr.readAll();
			System.out.println("FastaReader.ReadAll: "+fastaList.size()+" sequences read");
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Ignore
	@Test
	public void testReadAllString() {
		try {
			FastaReader fr = new FastaReader("/mnt/commons/test/biodata/example/sequences_exact_200000_100_20.fa");
			String regExp = "read_2[0-9]{5}";
			List<Fasta>fastaList = fr.readAll(regExp);
			System.out.println("FastaReader.ReadAllString: " + fastaList.size() + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fr.close();
			
			FastaReader fr2 = new FastaReader("/mnt/commons/test/biodata/example/sequences_exact_200000_100_20.fa");
			regExp = "read_1999[0-9]{2}";
			fastaList = fr2.readAll(regExp);
			System.out.println("FastaReader.ReadAllString: " + fastaList.size() + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fr2.close();
			
			FastaReader fr3 = new FastaReader("/mnt/commons/test/biodata/example/sequences_exact_200000_100_20.fa");
			regExp = "read_[0-9]+";
			fastaList = fr3.readAll(regExp);
			System.out.println("FastaReader.ReadAllString: " + fastaList.size() + " sequences whose Id matches regular Expresion '" + regExp + "'");
			fr3.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
