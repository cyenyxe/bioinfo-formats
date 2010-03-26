package org.bioinfo.formats.io.reader;

import static org.junit.Assert.fail;

import org.bioinfo.formats.core.feature.Bed;
import org.junit.Ignore;
import org.junit.Test;

public class BedReaderTest {

	public void testBedReaderString() {
		fail("Not yet implemented");
	}

	public void testGetAll() {
		fail("Not yet implemented");
	}

	public void testGrep() {
		fail("Not yet implemented");
	}

	@Ignore // Probando Fasta Readers
	@Test
	public void testNext() {
		BedReader br;
		try {
			br = new BedReader("/mnt/commons/test/biodata/example/snps.bed");
			Bed b = br.read();
			System.out.println(b.toString());
			b = br.read();
			System.out.println(b.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}

}
