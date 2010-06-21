package org.bioinfo.formats.parser.biopax;

import java.io.IOException;

import org.jdom.JDOMException;
import org.junit.Test;


public class BioPaxParserTest {

	@Test
	public void Test() {
		//String filename = "/home/jtarraga/bioinfo/reactome/Bos taurus.owl";
		String filename = "/home/jtarraga/bioinfo/reactome/Homo sapiens.owl";
		//String filename = "/home/jtarraga/bioinfo/reactome/Felis catus.owl";

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