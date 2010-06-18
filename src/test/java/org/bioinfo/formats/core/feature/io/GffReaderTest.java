package org.bioinfo.formats.core.feature.io;

import static org.junit.Assert.fail;

import java.util.List;

import org.bioinfo.formats.core.feature.Gff;
import org.junit.Test;

public class GffReaderTest {

	
	@Test
	public void testRead() {
		System.out.println("\nTest 1 - read()");
		try {
			GffReader gffReader = new GffReader("/mnt/commons/test/formats/features/snps.gff");
			Gff gff = null;
			while((gff = gffReader.read()) != null) {
				System.out.println(gff.toString());
			}
			gffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
	
	@Test
	public void testReadPattern() {
		System.out.println("\nTest 2 - readPattern()");
		try {
			GffReader gffReader = new GffReader("/mnt/commons/test/formats/features/snps.gff");
			Gff gff = null;
			while((gff = gffReader.read(".+957.+")) != null) {
				System.out.println(gff.toString());
			}
			gffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
	@Test
	
	public void testReadNumber() {
		System.out.println("\nTest 3 - readNumber()");
		try {
			GffReader gffReader = new GffReader("/mnt/commons/test/formats/features/snps.gff");
			List<Gff> gffs = gffReader.read(5);
			System.out.println(gffs.size()+" data: "+gffs.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testReadAll() {
		System.out.println("\nTest 4 - readAll()");
		try {
			GffReader gffReader = new GffReader("/mnt/commons/test/formats/features/snps.gff");
			List<Gff> gffs = gffReader.readAll();
			System.out.println(gffs.size()+" data: "+gffs.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testReadAllPattern() {
		System.out.println("\nTest 5 - readAllPattern()");
		try {
			GffReader gffReader = new GffReader("/mnt/commons/test/formats/features/snps.gff");
			List<Gff> gffs = gffReader.readAll(".+957.+");
			System.out.println(gffs.size()+" data: "+gffs.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
