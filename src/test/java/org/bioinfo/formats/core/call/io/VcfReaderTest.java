package org.bioinfo.formats.core.call.io;

import static org.junit.Assert.fail;

import org.bioinfo.formats.core.call.Vcf4;
import org.bioinfo.formats.core.call.vcf4.VcfRecord;
import org.junit.Test;

public class VcfReaderTest {

	@Test
	public void testRead() {
		System.out.println("\nTest 1 - read()");

		try {
			Vcf4Reader vcf4Reader = new Vcf4Reader("/mnt/commons/test/formats/features/example_1000genomes_40000.vcf");
			
			Vcf4 vcf4 = vcf4Reader.parse(); 
			
			System.out.println(vcf4.getRecords().toString());
			
//			VcfRecord vcfRecord = null;
//			while((vcfRecord = vcf4Reader.read()) != null) {
////				System.out.println(vcfRecord.toString());
//			}
			vcf4Reader.close();
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
