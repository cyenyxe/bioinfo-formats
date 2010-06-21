package org.bioinfo.formats.core.feature.io;

import static org.junit.Assert.fail;

import java.util.List;

import org.bioinfo.formats.core.feature.Bed;
import org.junit.Test;

public class BedReaderTest {

	
	@Test
	public void testRead() {
		try {
			BedReader bedReader = new BedReader("/mnt/commons/test/formats/features/snps.bed");
			Bed bed = null;
			while((bed = bedReader.read()) != null) {
				System.out.println(">>"+bed.toString());
			}
			bedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}

	@Test
	public void testReadAll() {
		try {
			BedReader bedReader = new BedReader("/mnt/commons/test/formats/features/snps.bed");
			List<Bed> beds = bedReader.readAll();
			System.out.println(beds.size()+" data: "+beds.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
}
