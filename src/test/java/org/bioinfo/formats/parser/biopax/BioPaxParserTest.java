package org.bioinfo.formats.parser.biopax;

import org.junit.Test;


public class BioPaxParserTest {

	@Test
	public void Test() {
		//String filename = "/mnt/commons/formats/reactome/Bos taurus.owl";
		String filename = "/mnt/commons/formats/reactome/Homo sapiens.owl";
		//String filename = "/mnt/commons/formats/reactome/Felis catus.owl";

		try {
			BioPaxParser parser = new BioPaxParser(filename);
			BioPax bioPax = parser.parse();
			
			System.out.println("------------------------------------\n");
			System.out.println(bioPax.toString());
			System.out.println("\n------------------------------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
