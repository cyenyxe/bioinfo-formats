package org.bioinfo.formats.core.feature.io;

import static org.junit.Assert.fail;

import java.util.List;

import org.bioinfo.formats.core.feature.Gff;
import org.junit.Test;

public class GffReaderTest {

	
	@Test
	public void testRead() {
		try {
			GffReader gffReader = new GffReader("/mnt/commons/test/formats/features/snps.gff");
			Gff gff = null;
			while((gff = gffReader.read()) != null) {
				System.out.println(">>"+gff.toString());
			}
			gffReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}

	@Test
	public void testReadAll() {
		try {
			GffReader gffReader = new GffReader("/mnt/commons/test/formats/features/snps.gff");
			List<Gff> gffs = gffReader.readAll();
			System.out.println(gffs.size()+" data: "+gffs.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
