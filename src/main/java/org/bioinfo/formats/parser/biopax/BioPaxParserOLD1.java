package org.bioinfo.formats.parser.biopax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.biopax.paxtools.controller.Fetcher;
import org.biopax.paxtools.controller.PropertyEditor;
import org.biopax.paxtools.controller.PropertyFilter;
import org.biopax.paxtools.controller.Traverser;
import org.biopax.paxtools.controller.Visitor;
import org.biopax.paxtools.examples.ProteinNameLister;
import org.biopax.paxtools.io.simpleIO.SimpleEditorMap;
import org.biopax.paxtools.io.simpleIO.SimpleReader;
import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level2.pathway;
import org.biopax.paxtools.model.level2.physicalEntity;
import org.biopax.paxtools.model.level2.protein;
import org.biopax.paxtools.model.level2.unificationXref;
import org.biopax.paxtools.util.ClassFilterSet;

public class BioPaxParserOLD1 {

	private static Log log = LogFactory.getLog(ProteinNameLister.class);
	private static Fetcher fetcher;

	// --------------------------- main() method ---------------------------

	public BioPaxParserOLD1(String pathname) {

		System.out.println("starting with " + pathname + "...");

		SimpleReader reader = new SimpleReader(BioPAXLevel.L3);
		
		try {
			process(pathname, reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void process(String pathname, SimpleReader reader) throws FileNotFoundException
	{
		System.out.println("--------------" + pathname + "---------");
		Model model = reader.convertFromOWL(new FileInputStream(pathname));
		listProteinUnificationXrefsPerPathway(model);
	}


	public static void listProteinUnificationXrefsPerPathway(Model model)
	{
		Set<pathway> pathways = model.getObjects(pathway.class);
		for (pathway aPathway : pathways)
		{
			//printout aPathway's name
			System.out.println(aPathway.getNAME());

			//Use new fetcher to get all dependents in a new level2
			Model onePathwayModel =
				BioPAXLevel.L2.getDefaultFactory().createModel();
			fetcher.fetch(aPathway, onePathwayModel);

			//get all proteins in the new level2
			Set<protein> proteins =
				onePathwayModel.getObjects(protein.class);

			//iterate and print names
			for (protein aProtein : proteins)
			{
				System.out.println("\t" + aProtein.getNAME());
				//now list xrefs and print if uni
				Set<unificationXref> xrefs =
					new ClassFilterSet<unificationXref>(
							aProtein.getXREF(), unificationXref.class);
					for (unificationXref x : xrefs)
					{
						System.out.println("\t\t" + x.getDB() + ":" + x.getID());
					}
			}
		}

	}

	/**
	 * Here is a more elegant way of doing the previous method!
	 * 
	 * @param model
	 */
	public static void listUnificationXrefsPerPathway(Model model)
	{
		// This is a visitor for elements in a pathway - direct and indirect
		Visitor visitor = new Visitor()
		{
			public void visit(BioPAXElement domain, Object range, Model model, PropertyEditor editor)
			{
				if (range instanceof physicalEntity)
				{
					// Do whatever you want with the pe and xref here
					physicalEntity pe = (physicalEntity) range;
					ClassFilterSet<unificationXref> unis=
						new ClassFilterSet<unificationXref>(pe.getXREF(),
								unificationXref.class);
						for (unificationXref uni : unis)
						{
							System.out.println("pe.getNAME() = " + pe.getNAME());
							System.out.println("uni = " + uni.getID());
						}
				} 
			}
		};

		Traverser traverser = new Traverser(new SimpleEditorMap(BioPAXLevel.L2), visitor);

		Set<pathway> pathways = model.getObjects(pathway.class);
		for (pathway pathway : pathways)
		{
			traverser.traverse(pathway,model);
		}
	}
}
