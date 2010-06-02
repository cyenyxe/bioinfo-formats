package org.bioinfo.formats.io.reader;

import static org.junit.Assert.*;

import java.util.List;

import org.bioinfo.formats.core.feature.Qseq;
import org.junit.Test;

public class QseqReaderTest {

	@Test
	public void testSize() {
		QseqReader reader;
		try {
			reader = new QseqReader("/mnt/commons/test/biodata/qseq/prueba.qseq");
			int size = reader.size();
			assert (size == 100000);
			reader.close();			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());		
		}
	}

	@Test
	public void testClose() {
		QseqReader reader;
		try {
			reader = new QseqReader("/mnt/commons/test/biodata/qseq/prueba.qseq");
			reader.close();			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());		
		}
	}
	
	@Test
	public void testRead() {
		QseqReader reader;
		try {
			reader = new QseqReader("/mnt/commons/test/biodata/qseq/prueba.qseq");
			Qseq qseq;
			int cont = 0;
			while ((qseq = reader.read()) != null) {
				if (cont < 10) {
					System.out.println(qseq);
				}
				cont ++;
			}
			reader.close();			
			System.out.println("QseqReaderTest.testRead: " + cont + " sequences read\n");
			assert (cont == 100000);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());		
		}
	}	

	@Test
	public void testReadString() {
		QseqReader reader;
		try {
			reader = new QseqReader("/mnt/commons/test/biodata/qseq/prueba.qseq");
			Qseq qseq;
			int cont = 0;			
			while ((qseq = reader.read("ILLUMINA-G2")) != null) {
				System.out.println(qseq);
				cont++;
			}
			reader.close();		
			assert (cont == 3);			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());		
		}
	}
	
	@Test
	public void testQseqToFastQ() {
		QseqReader reader;
		try {
			reader = new QseqReader("/mnt/commons/test/biodata/qseq/prueba.qseq");
			Qseq qseq;
			int cont = 0;
			while ((qseq = reader.read()) != null && cont<10) {
				System.out.println(qseq.toFastQ());
				cont ++;
			}
			reader.close();			
			System.out.println("QseqReaderTest.testQseqToFastQ: " + cont + " sequences read\n");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());		
		}
	}	
	
	@Test
	public void testReadAll() {
		QseqReader reader;
		try {
			reader = new QseqReader("/mnt/commons/test/biodata/qseq/prueba.qseq");
			List<Qseq> qseqList = reader.readAll();
			reader.close();				
			System.out.println("QseqReaderTest.testReadAll: " + qseqList.size() + " sequences read\n");			
			assert(qseqList.size() == 100000);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());		
		}
	}

	@Test
	public void testReadAllString() {
		QseqReader reader;
		try {
			reader = new QseqReader("/mnt/commons/test/biodata/qseq/prueba.qseq");
			List<Qseq> qseqList = reader.readAll("ILLUMINA-G2");
			reader.close();				
			assert(qseqList.size() == 3);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());		
		}
	}

}
