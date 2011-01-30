package org.bioinfo.formats.core.call.io;

import static org.junit.Assert.fail;

import org.bioinfo.formats.core.call.VcfRecord;
import org.junit.Test;

public class VcfReaderTest {

	@Test
	public void testRead() {
		System.out.println("\nTest 1 - read()");
		try {
			VcfReader vcfReader = new VcfReader("/mnt/commons/test/formats/features/snps.vcf");
			VcfRecord vcfRecord = null;
			while((vcfRecord = vcfReader.read()) != null) {
				System.out.println(vcfRecord.toString());
			}
			vcfReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testReadString() {
		fail("Not yet implemented");
	}

	public void testReadInt() {
		fail("Not yet implemented");
	}

	public void testReadAll() {
		fail("Not yet implemented");
	}

	public void testReadAllString() {
		fail("Not yet implemented");
	}

	public void testGetFileFormat() {
		fail("Not yet implemented");
	}

	public void testGetInfos() {
		fail("Not yet implemented");
	}

	public void testGetFilters() {
		fail("Not yet implemented");
	}

	public void testGetFormats() {
		fail("Not yet implemented");
	}

}
