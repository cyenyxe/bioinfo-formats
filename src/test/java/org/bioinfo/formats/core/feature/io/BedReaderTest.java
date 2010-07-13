package org.bioinfo.formats.core.feature.io;

import static org.junit.Assert.fail;

import java.util.List;

import org.bioinfo.formats.core.feature.Bed;
import org.bioinfo.formats.core.feature.Gff;
import org.junit.Test;

public class BedReaderTest {

	
	@Test
	public void testRead() {
		System.out.println("\nTest 1 - read()");
		try {
			BedReader bedReader = new BedReader("/mnt/commons/test/formats/features/snps.bed");
			Bed bed = null;
			while((bed = bedReader.read()) != null) {
				System.out.println(bed.toString());
			}
			bedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
	
	@Test
	public void testReadPattern() {
		System.out.println("\nTest 2 - readPattern()");
		try {
			BedReader bedReader = new BedReader("/mnt/commons/test/formats/features/snps.bed");
			Bed bed = null;
			while((bed = bedReader.read(".+957.+")) != null) {
				System.out.println(bed.toString());
			}
			bedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
	@Test
	
	public void testReadNumber() {
		System.out.println("\nTest 3 - readNumber()");
		try {
			BedReader bedReader = new BedReader("/mnt/commons/test/formats/features/snps.bed");
			List<Bed> beds = bedReader.read(5);
			System.out.println(beds.size()+" data: "+beds.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testReadAll() {
		System.out.println("\nTest 4 - readAll()");
		try {
			BedReader bedReader = new BedReader("/mnt/commons/test/formats/features/snps.bed");
			List<Bed> beds = bedReader.readAll();
			System.out.println(beds.size()+" data: "+beds.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	@Test
	public void testReadAllPattern() {
		System.out.println("\nTest 5 - readAllPattern()");
		try {
			BedReader bedReader = new BedReader("/mnt/commons/test/formats/features/snps.bed");
			List<Bed> beds = bedReader.readAll(".+957.+");
			System.out.println(beds.size()+" data: "+beds.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
}
