package org.bioinfo.formats.io.reader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.bioinfo.formats.core.sequence.Qual;
import org.bioinfo.formats.io.exception.FileFormatException;
import org.junit.Test;

public class QualReaderTest {

	@Test
	public void testSize() {
		QualReader reader;
		try {
			System.out.println("\ntestSize\n");
			reader = new QualReader("/mnt/commons/test/biodata/test.qual");
			assert(reader.size() == 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		} catch (FileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		}
	}

	@Test
	public void testClose() {
		QualReader reader;
		try {
			System.out.println("\ntestClose\n");			
			reader = new QualReader("/mnt/commons/test/biodata/test.qual");
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		}
	}

	@Test
	public void testRead() {
		QualReader reader;
		try {
			System.out.println("\ntestRead\n");						
			reader = new QualReader("/mnt/commons/test/biodata/test.qual");
			int cont = 0;
			Qual qual;
			while ((qual = reader.read()) != null) {
				cont++;
				System.out.println(qual);
			}
			assert(cont == 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		} catch (FileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		}
	}
	

	@Test
	public void testReadString() {
		QualReader reader;
		try {
			System.out.println("\ntestReadString\n");				
			reader = new QualReader("/mnt/commons/test/biodata/test.qual");
			int cont = 0;
			Qual qual;
			while ((qual = reader.read("GCI4HUN01C8CSY")) != null) {
				cont++;
				System.out.println(qual);
			}
			assert(cont == 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		} catch (FileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		}
	}

	@Test
	public void testReadAll() {
		QualReader reader;
		try {
			System.out.println("\ntestReadAll\n");						
			reader = new QualReader("/mnt/commons/test/biodata/test.qual");
			List<Qual> listQual = reader.readAll();
			assert(listQual.size() == 10);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		} catch (FileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		}
	}

	@Test
	public void testReadAllString() {
		QualReader reader;
		try {
			System.out.println("\ntestReadAllString\n");					
			reader = new QualReader("/mnt/commons/test/biodata/test.qual");
			List<Qual> listQual = reader.readAll("GCI4HUN01C8CSY");
			assert(listQual.size() == 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		} catch (FileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());					
		}
	}

}
