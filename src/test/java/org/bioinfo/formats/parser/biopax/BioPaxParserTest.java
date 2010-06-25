package org.bioinfo.formats.parser.biopax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bioinfo.commons.utils.ListUtils;
import org.junit.Test;


public class BioPaxParserTest {

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

	@Test
	public void AnaTest() {
		//String filename = "/mnt/commons/formats/reactome/Bos taurus.owl";
		String filename = "/mnt/commons/formats/reactome/Homo sapiens.owl";
		//String filename = "/mnt/commons/formats/reactome/Felis catus.owl";

		try {
			BioPaxElement bioPaxElement;
			BioPaxParser parser = new BioPaxParser(filename);
			BioPax bioPax = parser.parse();
			
			System.out.println("------------------------------------\n");
			Map<String,BioPaxElement> mapElement = bioPax.getElementMap();
			
			String name;
			Map<String,Integer> mapCounter = new HashMap<String,Integer>();
			for(String key: mapElement.keySet()) {
				name = mapElement.get(key).getBioPaxClassName(); 
				if (!mapCounter.containsKey(name)) {
					mapCounter.put(name, 0);
				}
				mapCounter.put(name, mapCounter.get(name) + 1);
			}
			for(String key: mapCounter.keySet()) {
				System.out.println(key + "\t" + mapCounter.get(key));
			}
			
			boolean ini = true;
						
			List<String> interactionList = bioPax.getInteractionList();
			System.out.println("number of interactions = " + interactionList.size());

//			List<String> from, to;
//			List<String> proteinFrom, proteinTo;
//			
//			Map<String,Integer> mapFrom = new HashMap<String,Integer>(); 
//			Map<String,Integer> mapTo = new HashMap<String,Integer>(); 
//
//			for(String key: interactionList) {
//				bioPaxElement = mapElement.get(key);
//				from = null;
//				to = null;
//				if (ini) {
//					System.out.println(bioPaxElement.toString());
//					ini = false;
//				}
//				
//				if (bioPaxElement.getParams().containsKey("controller-id") && bioPaxElement.getParams().containsKey("controlled-id")) {
//					from = bioPaxElement.getParams().get("controller-id");
//					to = bioPaxElement.getParams().get("controlled-id");
//				} else if (bioPaxElement.getParams().containsKey("left-id") && bioPaxElement.getParams().containsKey("rigth-id")) {
//					from = bioPaxElement.getParams().get("left-id");
//					to = bioPaxElement.getParams().get("right-id");					
//				}
//				
//				if (from!=null && to!=null) {
//					String msg = "";
//					msg += bioPaxElement.getBioPaxClassName() + "\t\t: ";
//					for(String item: from) {
//						msg += item + "(" + mapElement.get(item).getBioPaxClassName() + "), ";
//						if (!mapFrom.containsKey(mapElement.get(item).getBioPaxClassName())) {
//							mapFrom.put(mapElement.get(item).getBioPaxClassName(), 0);
//						}
//						mapFrom.put(mapElement.get(item).getBioPaxClassName(), mapFrom.get(mapElement.get(item).getBioPaxClassName()) + 1);
//					}
//					msg += " >>>> ";
//					for(String item: to) {
//						msg += item + "(" + mapElement.get(item).getBioPaxClassName() + "), ";
//						if (!mapTo.containsKey(mapElement.get(item).getBioPaxClassName())) {
//							mapTo.put(mapElement.get(item).getBioPaxClassName(), 0);
//						}
//						mapTo.put(mapElement.get(item).getBioPaxClassName(), mapTo.get(mapElement.get(item).getBioPaxClassName()) + 1);
//					}
//					System.out.println(msg);
//					//System.out.println(bioPaxElement.getBioPaxClassName() + ": " + ListUtils.toString(from) + " -> " + ListUtils.toString(to));
//				}
//			}
//			
//			System.out.println("From componets...");
//			for(String key: mapFrom.keySet()) {
//				System.out.println("\t" + key + "\t" + mapFrom.get(key));
//			}
//
//			System.out.println("To componets...");
//			for(String key: mapTo.keySet()) {
//				System.out.println("\t" + key + "\t" + mapTo.get(key));
//			}
			
			Map<String,Integer> visitedStepMap = new HashMap<String,Integer>();
			List<String> pathwayList = bioPax.getPathwayList();
			System.out.println("number of pathways = " + pathwayList.size());
			for(int i=0 ; i<1 ; i++) {
				bioPaxElement = mapElement.get(pathwayList.get(i));
				
				System.out.println("------------------------------------------------");
				System.out.println(bioPaxElement.toString());
				System.out.println("------");
				
				if (bioPaxElement.getParams().containsKey("pathwayOrder-id")) {
					for(String step: bioPaxElement.getParams().get("pathwayOrder-id")) {
						if (!visitedStepMap.containsKey(step)) {
							System.out.println("\tstep  = " + step);
							visitedStepMap.put(step, 1);
						}
					}
				}
				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
