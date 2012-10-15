package org.bioinfo.formats.parser.biopax;


import java.io.File;
import java.util.List;

import org.bioinfo.commons.io.utils.IOUtils;
import org.bioinfo.commons.utils.ListUtils;
import org.junit.Test;

import com.google.gson.Gson;

public class ProteinInteractionTest {

	@Test
	public void Test() {
		String filename = "/mnt/commons/formats/reactome/Homo sapiens.owl";

		try {
			BioPaxParser parser = new BioPaxParser(filename);
			BioPax bioPax = parser.parse();
			
			Gson gson = new Gson();
			IOUtils.write(new File("/tmp/biopax.json"), gson.toJson(bioPax));
			
			System.out.println("getting protein interactions...");
			ProteinInteraction pi = new ProteinInteraction(bioPax);
			List<String> list = pi.getList();
			System.out.println(ListUtils.toString(list, "\n"));
			
			IOUtils.write("/tmp/interactions.txt", ListUtils.toString(list, "\n"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
