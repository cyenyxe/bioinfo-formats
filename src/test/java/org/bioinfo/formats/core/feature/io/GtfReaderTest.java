package org.bioinfo.formats.core.feature.io;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.bioinfo.formats.core.feature.Gtf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GtfReaderTest {
	
	private String filePath = "/home/imedina/Downloads/Homo_sapiens.GRCh37.69.gtf";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRead() {
		System.out.println("\nTest 1 - read()");
		try {
			GtfReader gtfReader = new GtfReader(new File(filePath));
			Gtf gtf = null;
			int cont = 0;
			while((gtf = gtfReader.read()) != null) {
				System.out.println(gtf.toString());
				System.out.println(gtf.toJson());
				if(cont++ == 10) {
					break;
				}
			}
			gtfReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
	
	@Test
	public void testReadNumber() {
		System.out.println("\nTest 2 - readNumber()");
		try {
			GtfReader gtfReader = new GtfReader(new File(filePath));
			List<Gtf> gtfs = gtfReader.read(5);
			System.out.println(gtfs.size()+" data: "+gtfs.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testReadAll() {
		System.out.println("\nTest 3 - readAll()");
		try {
			GtfReader gtfReader = new GtfReader(new File(filePath));
			List<Gtf> gtfs = gtfReader.readAll();
			System.out.println(gtfs.size()+" data: "+gtfs.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}


}
