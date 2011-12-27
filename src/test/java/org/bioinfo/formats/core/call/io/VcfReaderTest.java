package org.bioinfo.formats.core.call.io;

import static org.junit.Assert.fail;

import org.bioinfo.commons.io.utils.IOUtils;
import org.bioinfo.formats.core.variant.Vcf4;
import org.bioinfo.formats.core.variant.io.Vcf4Reader;
import org.bioinfo.formats.core.variant.vcf4.filter.VcfInfoFilter;
import org.bioinfo.formats.core.variant.vcf4.filter.VcfRegionFilter;
import org.junit.Test;

public class VcfReaderTest {

	@Test
	public void testRead() {
		System.out.println("\nTest 1 - read()");

		try {
			Vcf4Reader vcf4Reader = new Vcf4Reader("/opt/variant/example/01-12156-ASW.vcf");
			
//			List<VcfGenericFilter> filters = new ArrayList<VcfGenericFilter>();
//			filters.add(new VcfRegionFilter("1", 50, 100000));
//			vcf4Reader.setVcfGenericFilters(filters);
			vcf4Reader.addFilter(new VcfRegionFilter("1", 50, 2000000));
			vcf4Reader.addFilter(new VcfRegionFilter("1", 18000000, 20000000));
			vcf4Reader.addFilter(new VcfInfoFilter("AF<0.05"));
			
			Vcf4 vcf4 = vcf4Reader.parse();
			
			
//			System.out.println(vcf4.getInfo().toString());
//			for(VcfRecord vcfRec: vcf4.getRecords()) {
//				System.out.println(vcfRec.toString());
//				
//			}
			IOUtils.write("/tmp/aaa.txt", vcf4.toString());
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
