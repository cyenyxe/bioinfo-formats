package org.bioinfo.formats.io.parser.uniprot;

import static org.junit.Assert.fail;

import javax.xml.bind.JAXBException;

import org.bioinfo.formats.io.parser.uniprot.v135jaxb.Entry;
import org.bioinfo.formats.io.parser.uniprot.v135jaxb.Uniprot;
import org.junit.Test;

public class UniprotParserTest {

	@Test
	public void testLoadXMLInfo() {
		UniprotParser up = new UniprotParser();
		
		try {
			Uniprot uniprot = (Uniprot) up.loadXMLInfo("/mnt/commons/test/uniprot/chunk_entry_001.xml");
			for(Entry entry: uniprot.getEntry()) {
				System.out.println(entry.getAccession()+" "+entry.getName());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
		
	}

}