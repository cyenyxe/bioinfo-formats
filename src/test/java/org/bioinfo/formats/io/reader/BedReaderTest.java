package org.bioinfo.formats.io.reader;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.bioinfo.formats.core.feature.Bed;
import org.junit.Test;

public class BedReaderTest {

	@Test
	public void testBedReaderString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGrep() {
		fail("Not yet implemented");
	}

	@Test
	public void testNext() {
		BedReader br;
		try {
			br = new BedReader("/tmp/snps.bed");
			Bed b = br.next();
			System.out.println(b.toString());
			b = br.next();
			System.out.println(b.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}

}
