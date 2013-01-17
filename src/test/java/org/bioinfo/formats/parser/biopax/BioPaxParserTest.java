package org.bioinfo.formats.parser.biopax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bioinfo.commons.utils.ListUtils;
//import org.bioinfo.infrared.lib.common.BiopaxPathway;
//import org.bioinfo.infrared.lib.common.Interaction;
//import org.bioinfo.infrared.lib.common.PhysicalEntity;
//import org.bioinfo.infrared.lib.common.SubPathway;
import org.bson.BSON;
import org.bson.BSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;


public class BioPaxParserTest {

	Map<String, Node> nodes = new HashMap<String, Node>();
	BioPax bioPax = null;

	public class Node {
		public String name = "";
		public String type = "";
		public List<String> prevNodes = null;
		public List<String> nextNodes = null;

		public Node(String name) {
			this.name = name;
			this.type = "-";
			this.prevNodes = new ArrayList<String>();
			this.nextNodes = new ArrayList<String>();
		}

		public Node(String name, String type) {
			this.name = name;
			this.type = type;
			this.prevNodes = new ArrayList<String>();
			this.nextNodes = new ArrayList<String>();
		}

		public void addNext(String name) {
			if (name!=null && name.length()>0) {
				nextNodes.add(name);
			}
		}

		public void addPrev(String name) {
			if (name!=null && name.length()>0) {
				prevNodes.add(name);
			}
		}

		public String toString() {			
			return type + "\t" + name + " >>> " + (nextNodes.size()>0 ? ListUtils.toString(nextNodes): "");
		}
	}

	
//	@Test
//	public void makeJson() {
//		String filename = "/mnt/commons/formats/reactome/Homo sapiens.owl";
//
//		try {
//			Mongo m = new Mongo("localhost", 27017);
//			DB db = m.getDB("reactome");
//			DBCollection coll = db.getCollection("pathway");		
//			BioPaxParser parser = new BioPaxParser(filename);
//			bioPax = parser.parse();
//			
//			List<BiopaxPathway> pathwayList = new ArrayList<BiopaxPathway>();
//			List<DBObject> dbObjList = new ArrayList<DBObject>();
//			Gson g = new Gson();
//			
//			System.out.println("Pathway list: ");
//			int cont = 0;
////			String pathway = "Initiation_of_checkpoint_signal_from_defective_kinetochores";
//			for(String pathway: bioPax.getPathwayList() ) {
//				System.out.println("Pathway: "+pathway);
//				if(cont++ == 1) break;
//				
//				String name = bioPax.getElementMap().get(pathway).getId();
//				Map<String, List<String>> params = bioPax.getElementMap().get(pathway).getParams();
//				List<String> species = params.get("organism-id");
//				List<String> displayName = params.get("displayName");
//				List<String> xref = params.get("xref-id");
//				BiopaxPathway p = new BiopaxPathway(name, "Reactome", "Reactome", "39", species, displayName, xref);
//				List<String> pathwayComponents = params.get("pathwayComponent-id");
//				
//				// loop pathway components
//				if(pathwayComponents != null) {
//					for(String component: pathwayComponents) {
//						String type = bioPax.getElementMap().get(component).getBioPaxClassName();
//						if(type.equalsIgnoreCase("Pathway")) {
//							SubPathway sp = searchSubPathways(component, p);
//							if(sp != null) p.subPathways.add(sp);
//						}
//						else if(type.equalsIgnoreCase("GeneticInteraction") || type.equalsIgnoreCase("MolecularInteraction") || type.equalsIgnoreCase("TemplateReaction") || type.equalsIgnoreCase("Catalysis") || type.equalsIgnoreCase("Modulation") || type.equalsIgnoreCase("TemplateReactionRegulation") || type.equalsIgnoreCase("BiochemicalReaction") || type.equalsIgnoreCase("ComplexAssembly") || type.equalsIgnoreCase("Degradation") || type.equalsIgnoreCase("Transport") || type.equalsIgnoreCase("TransportWithBiochemicalReaction")) {
//							Map<String, List<String>> interactionParams = bioPax.getElementMap().get(component).getParams();
//							p.interactions.add(new Interaction(bioPax.getElementMap().get(component).getId(), type, interactionParams));
////							System.out.println("Found interaction: "+type);
//							p.allInteractionsIDs.add(bioPax.getElementMap().get(component).getId());
//							
//							addPhysicalEntities(component, p, false);
//						}
////						else if(type.equalsIgnoreCase("PhysicalEntity") || type.equalsIgnoreCase("Complex") || type.equalsIgnoreCase("DNA") || type.equalsIgnoreCase("DNARegion") || type.equalsIgnoreCase("Protein") || type.equalsIgnoreCase("RNA") || type.equalsIgnoreCase("RNARegion") || type.equalsIgnoreCase("SmallMolecule")) {
////							System.out.println("Found physical entity: "+type);
////							p.physicalEntities.add(new PhysicalEntity(bioPax.getElementMap().get(component).getId()));
////						}
//					}
//				}
//				pathwayList.add(p);
//				dbObjList.add((DBObject)JSON.parse(g.toJson(p)));
//				
//				System.out.println("JSON: ");
//				System.out.println(g.toJson(p));
//				
//				BSONObject b = (BSONObject)JSON.parse(g.toJson(p));
//				System.out.println("BSON: ");
//				System.out.println(BSON.encode(b));
//				
//			}
////			String json = g.toJson(pathwayList);
////			System.out.println(json);
//
////			coll.insert(dbObjList);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public SubPathway searchSubPathways(String component, BiopaxPathway pathway) {
//		String type = bioPax.getElementMap().get(component).getBioPaxClassName();
//		
//		if(type.equalsIgnoreCase("Pathway")) {
//			SubPathway sp = new SubPathway(component);
//			if(bioPax.getElementMap().get(component).getParams().get("pathwayComponent-id") != null) {
//				sp.subPathways  = new ArrayList<SubPathway>();
////				System.out.println("Component ID with pathway components: "+bioPax.getElementMap().get(component).getId()+" -- "+bioPax.getElementMap().get(component).getParams().get("pathwayComponent-id").size());
//				for(String subcomponent: bioPax.getElementMap().get(component).getParams().get("pathwayComponent-id")) {
////					System.out.println("SubComponent ID: "+bioPax.getElementMap().get(subcomponent).getId());
//					SubPathway sp2 = searchSubPathways(subcomponent, pathway);
//					if(sp2 != null) sp.subPathways.add(sp2);
//				}
//			}
//			return sp;
//		}
//		else if(type.equalsIgnoreCase("GeneticInteraction") || type.equalsIgnoreCase("MolecularInteraction") || type.equalsIgnoreCase("TemplateReaction") || type.equalsIgnoreCase("Catalysis") || type.equalsIgnoreCase("Modulation") || type.equalsIgnoreCase("TemplateReactionRegulation") || type.equalsIgnoreCase("BiochemicalReaction") || type.equalsIgnoreCase("ComplexAssembly") || type.equalsIgnoreCase("Degradation") || type.equalsIgnoreCase("Transport") || type.equalsIgnoreCase("TransportWithBiochemicalReaction")) {
////			System.out.println("Found interaction: "+type+" in subpathway");
////			p.interactions.add(new Interaction(bioPax.getElementMap().get(component).getId()));
//			String interaction = bioPax.getElementMap().get(component).getId();
//			pathway.allInteractionsIDs.add(interaction);
//			addPhysicalEntities(interaction, pathway, true);
//		}
////		else if(type.equalsIgnoreCase("PhysicalEntity") || type.equalsIgnoreCase("Complex") || type.equalsIgnoreCase("DNA") || type.equalsIgnoreCase("DNARegion") || type.equalsIgnoreCase("Protein") || type.equalsIgnoreCase("RNA") || type.equalsIgnoreCase("RNARegion") || type.equalsIgnoreCase("SmallMolecule")) {
////			System.out.println("Found physical entity: "+type+" in subpathway");
//////			p.physicalEntities.add(new PhysicalEntity(bioPax.getElementMap().get(component).getId()));
////		}
//		else System.out.println("Another type: "+type+" found.");
//		return null;
//	}
//	
//	public void addPhysicalEntities(String interaction, BiopaxPathway pathway, boolean onlyIDs) {
//		String type = bioPax.getElementMap().get(interaction).getBioPaxClassName();
//		List<String> tempList = new ArrayList<String>();
//		
//		if(type.equalsIgnoreCase("GeneticInteraction") || type.equalsIgnoreCase("MolecularInteraction")) {
//			if(bioPax.getElementMap().get(interaction).getParams().get("participant") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("participant"));
//			}
//		}
//		else if(type.equalsIgnoreCase("TemplateReaction")) {
//			if(bioPax.getElementMap().get(interaction).getParams().get("template") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("template"));
//			}
//			if(bioPax.getElementMap().get(interaction).getParams().get("product") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("product"));
//			}
//		}
//		else if(type.equalsIgnoreCase("Catalysis")) {
//			if(bioPax.getElementMap().get(interaction).getParams().get("cofactor") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("cofactor"));
//			}
//			if(bioPax.getElementMap().get(interaction).getParams().get("controller") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("controller"));
//			}
//			if(bioPax.getElementMap().get(interaction).getParams().get("controlled") != null) {
//				for(String catalysis: bioPax.getElementMap().get(interaction).getParams().get("controlled")) {
//					addPhysicalEntities(catalysis, pathway, onlyIDs);
//				}
//			}
//		}
//		else if(type.equalsIgnoreCase("Modulation")) {
//			if(bioPax.getElementMap().get(interaction).getParams().get("controller") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("controller"));
//			}
//			if(bioPax.getElementMap().get(interaction).getParams().get("controlled") != null) {
//				for(String catalysis: bioPax.getElementMap().get(interaction).getParams().get("controlled")) {
//					addPhysicalEntities(catalysis, pathway, onlyIDs);
//				}
//			}
//		}
//		else if(type.equalsIgnoreCase("TemplateReactionRegulation")) {
//			if(bioPax.getElementMap().get(interaction).getParams().get("controller") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("controller"));
//			}
//			if(bioPax.getElementMap().get(interaction).getParams().get("controlled") != null) {
//				for(String catalysis: bioPax.getElementMap().get(interaction).getParams().get("controlled")) {
//					addPhysicalEntities(catalysis, pathway, onlyIDs);
//				}
//			}
//		}
//		else if(type.equalsIgnoreCase("BiochemicalReaction") || type.equalsIgnoreCase("ComplexAssembly") || type.equalsIgnoreCase("Degradation") || type.equalsIgnoreCase("Transport") || type.equalsIgnoreCase("TransportWithBiochemicalReaction")) {
//			if(bioPax.getElementMap().get(interaction).getParams().get("left-id") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("left-id"));
//			}
//			if(bioPax.getElementMap().get(interaction).getParams().get("right-id") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("right-id"));
//			}
//			if(bioPax.getElementMap().get(interaction).getParams().get("participant") != null) {
//				tempList.addAll(bioPax.getElementMap().get(interaction).getParams().get("participant"));
//			}
//		}
//		
//		if(onlyIDs) {
//			for(String entity: tempList) {
//				pathway.allEntitiesIDs.add(entity);
//			}
//		}
//		else {
//			for(String entity: tempList) {
//				if(bioPax.getElementMap().get(entity) != null) {
//					String entityType = bioPax.getElementMap().get(entity).getBioPaxClassName();
//					Map<String, List<String>> params = bioPax.getElementMap().get(entity).getParams();
//					pathway.physicalEntities.add(new PhysicalEntity(entity, entityType, params));
//					
//					pathway.allEntitiesIDs.add(entity);					
//				}
//			}
//		}
//	}
	
//	@Test
	public void Test0() {
//		String filename = "/mnt/commons/formats/reactome/Homo sapiens.owl";
//		
//		try {
//			BioPaxParser parser = new BioPaxParser(filename);
//			bioPax = parser.parse();
//			
//			visitNode("Dissociation_of_beta_catenin_from_Axin_and_association_of_beta_catenin_with_phospho__20_aa__APC_in_the_detruction_complex", "");
//
//			String name = "Signaling_by_Wnt";
//			//name = "Degradation_of_beta_catenin_by_the_destruction_complex";
//			
//			System.out.println("************** " + bioPax.getElementMap().get(name).getBioPaxClassName() + " : " + bioPax.getElementMap().get(name).getId());
//
//			List<String> componentIds = getComponents(name, "");
//			System.out.println("componentIds = " + componentIds.size());
//			for(String id: componentIds) {
//				System.out.println("**** " + bioPax.getElementMap().get(id).getBioPaxClassName() + " : " + bioPax.getElementMap().get(id).getId());
//			}

			//			BioPaxElement e = bioPax.getElementMap().get(name);
			//			if ("pathway".equalsIgnoreCase(e.getBioPaxClassName())) {
			//				if (e.getParams().containsKey("pathwayOrder-id")) {
			//					List<String> orderIds = e.getParams().get("pathwayOrder-id");
			//					// here we have all PathwayStep IDs
			//					for(String id: orderIds) {
			//						BioPaxElement pathwayStep = bioPax.getElementMap().get(id);
			//						// stepIds,
			//						List<String> stepIds = pathwayStep.getParams().get("stepProcess-id");
			//						List<String> nextStepIds = pathwayStep.getParams().get("nextStep-id");
			//
			//						if (nextStepIds != null) {
			//							for(String nextStepId: nextStepIds) {
			//								BioPaxElement pathwayNextStep = bioPax.getElementMap().get(nextStepId);
			//								List<String> stepStepIds = pathwayNextStep.getParams().get("stepProcess-id");
			//								for(String stepId: stepIds) {
			//									if (nodes.containsKey(stepId)) {
			//				
			//				if (e.getParams().containsKey("pathwayComponent-id")) {
			//					List<String> componentIds = e.getParams().get("pathwayComponent-id");
			//					for(String id: componentIds) {
			//						if (!nodes.containsKey(id)) {
			//							nodes.put(id, new Node(id, bioPax.getElementMap().get(id).getBioPaxClassName()));
			//							updateNode(id);
			//						}
			//					}
			//				}
			//			}
			//			
			//			
			//			
			//			nodes.put(name, new Node(name, "pathway"));

			//			updateNode(name);
			//			for (String key: nodes.keySet()) {
			//				updateStep(key, null);
			//			}
			//
			//			int i=0;	

			//			for (String key: nodes.keySet()) {
			//				System.out.println((++i) + ":\t" + nodes.get(key).toString());
			//			}
			//displayEntity(bioPax, name, "*****");

//			//System.out.println(bioPax.toString());
//			System.out.println("\n------------------------------------");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public List<String> getComponents(String name, String tab) {
		//		System.out.println(tab + ", in: " + name);
		List<String> componentIds = new ArrayList<String> ();
		BioPaxElement ee, e = bioPax.getElementMap().get(name);

		if ("pathway".equalsIgnoreCase(e.getBioPaxClassName())) {
			if (e.getParams().containsKey("pathwayComponent-id")) {
				List<String> ids = e.getParams().get("pathwayComponent-id");
				for(String id: ids) {
					componentIds.addAll(getComponents(id, tab + " --- "));
				}
			}
		} else {
			componentIds.add(name);			
		}

		//		System.out.println(tab + ", out: " + ListUtils.toString(componentIds));
		return componentIds;
	}


	public void visitNode(String name, String indent) {
		BioPaxElement e = bioPax.getElementMap().get(name);

		System.out.println(indent + " : " + e.getBioPaxClassName() + " -> " + name + ( "protein".equalsIgnoreCase(e.getBioPaxClassName()) || "physicalentity".equalsIgnoreCase(e.getBioPaxClassName()) ? ", biopax name = " + e.getParams().get("name") : ""));

		if ("pathway".equalsIgnoreCase(e.getBioPaxClassName())) {
			if (e.getParams().containsKey("pathwayOrder-id")) {
				List<String> pathwayStepIds = e.getParams().get("pathwayOrder-id");
				for(String pathwayStepId: pathwayStepIds) {
					BioPaxElement pathwayStep = bioPax.getElementMap().get(pathwayStepId);
					List<String> stepIds = pathwayStep.getParams().get("stepProcess-id");
					List<String> nextStepIds = pathwayStep.getParams().get("nextStep-id");
					if (nextStepIds!=null) {
						for(String nextStepId: nextStepIds) {
							BioPaxElement nextStep = bioPax.getElementMap().get(nextStepId);
							List<String> stepStepIds = nextStep.getParams().get("stepProcess-id");
							if (stepStepIds!=null) {
								for(String stepStepId: stepStepIds) {
									visitNode(stepStepId, indent+" --- ");
								}
							}
						}
					}
				}
			}
		} else if ("biochemicalreaction".equalsIgnoreCase(e.getBioPaxClassName())) {
			List<String> ids = e.getParams().get("left-id");
			if (ids!=null) {
				for(String id: ids) {
					visitNode(id, indent + " --- left  --- ");
				}
			}
			ids = e.getParams().get("right-id");
			if (ids!=null) {
				for(String id: ids) {
					visitNode(id, indent + " --- right --- ");
				}
			}
		} else if ("catalysis".equalsIgnoreCase(e.getBioPaxClassName())) {
			List<String> ids = e.getParams().get("controller-id");
			if (ids!=null) {
				for(String id: ids) {
					visitNode(id, indent + " --- controller (" + e.getParams().get("controlType") +  ") --- ");
				}
			}
			ids = e.getParams().get("controlled-id");
			if (ids!=null) {
				for(String id: ids) {
					visitNode(id, indent + " --- controlled (" + e.getParams().get("controlType") +  ") --- ");
				}
			}
		} else if ("complex".equalsIgnoreCase(e.getBioPaxClassName())) {
			List<String> ids = e.getParams().get("component-id");
			if (ids!=null) {
				for(String id: ids) {
					visitNode(id, indent + " --- ");
				}
			}
		} else if ("smallmolecule".equalsIgnoreCase(e.getBioPaxClassName())) {
			System.out.println(indent + " --- >>>>>>> : small molecule");
		} else if ("protein".equalsIgnoreCase(e.getBioPaxClassName())) {
			List<String> ids = e.getParams().get("entityReference-id");
			if (ids!=null) {
				for(String id: ids) {
					visitNode(id, indent + " --- ");
				}
			}
		} else if ("proteinreference".equalsIgnoreCase(e.getBioPaxClassName())) {
			List<String> names = e.getParams().get("name");
			System.out.println(indent + " --- : protein names : " + (names!=null? ListUtils.toString(names, " , "): "unknown"));
		} else {
			System.out.println(indent + " --- >>>>>>> " + e.getBioPaxClassName() + " : " + e.getId());
		}
		printMap(indent + " +++	 ", e.getParams());
	}


	public void printMap(String indent, Map<String, List<String>> map) {
		for(String key: map.keySet()) {
			System.out.println(indent + " : " + key + " -> " + map.get(key));
		}
	}

	public void updateNode(String name) {
		Node node = nodes.get(name);
		BioPaxElement e = bioPax.getElementMap().get(name);

		if ("pathway".equalsIgnoreCase(node.type)) {
			if (e.getParams().containsKey("pathwayComponent-id")) {
				List<String> componentIds = e.getParams().get("pathwayComponent-id");
				for(String id: componentIds) {
					if (!nodes.containsKey(id)) {
						nodes.put(id, new Node(id, bioPax.getElementMap().get(id).getBioPaxClassName()));
						updateNode(id);
					}
				}
			}
		}
	}

	public void updateStep(String name, String parent) {
		if (parent!=null && !nodes.get(name).prevNodes.contains(parent)) {
			nodes.get(name).prevNodes.add(parent);
		}
		if (parent!=null && !nodes.get(parent).nextNodes.contains(name)) {
			nodes.get(parent).nextNodes.add(name);
		}
		//nodes.get(name).addPrev(parent);
		Node node = nodes.get(name);

		BioPaxElement e = bioPax.getElementMap().get(name);

		if ("pathway".equalsIgnoreCase(node.type)) {
			if (e.getParams().containsKey("pathwayOrder-id")) {
				List<String> orderIds = e.getParams().get("pathwayOrder-id");
				// here we have all PathwayStep IDs
				for(String id: orderIds) {
					BioPaxElement pathwayStep = bioPax.getElementMap().get(id);
					// stepIds,
					List<String> stepIds = pathwayStep.getParams().get("stepProcess-id");
					List<String> nextStepIds = pathwayStep.getParams().get("nextStep-id");

					if (nextStepIds != null) {
						for(String nextStepId: nextStepIds) {
							BioPaxElement pathwayNextStep = bioPax.getElementMap().get(nextStepId);
							List<String> stepStepIds = pathwayNextStep.getParams().get("stepProcess-id");
							for(String stepId: stepIds) {
								if (nodes.containsKey(stepId)) {
									for(String stepStepId: stepStepIds) {
										if (nodes.containsKey(stepStepId)) {
											updateStep(stepId, stepStepId);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void Test() {
		//String filename = "/mnt/commons/formats/reactome/Bos taurus.owl";
		String filename = "/mnt/commons/formats/reactome/Homo sapiens.owl";
		//String filename = "/mnt/commons/formats/reactome/Felis catus.owl";

		try {
			BioPaxParser parser = new BioPaxParser(filename);
			BioPax bioPax = parser.parse();


			int empties = 0;
			for(String name: bioPax.getPathwayList()) {
				if (!bioPax.getElementMap().get(name).getParams().containsKey("pathwayComponent-id")) {
					empties++;
				}
			}
			int proteins = 0;
			for(String name: bioPax.getPhysicalEntityList()) {
				if (bioPax.getElementMap().get(name).getBioPaxClassName().equalsIgnoreCase("protein")) {
					proteins++;
				}
			}

			System.out.println("number of genes = " + bioPax.getGeneList().size());
			System.out.println("number of protein = " + proteins);
			System.out.println("number of pathways = " + bioPax.getPathwayList().size());
			System.out.println("number of empty pathways = " + empties);
			System.out.println("number of interactions = " + bioPax.getInteractionList().size());

			System.out.println("------------------------------------\n");
			String name = "Signaling_by_Wnt";
			displayEntity(bioPax, name, "*****");
			//			System.out.println("Signaling_by_Wnt = " + bioPax.getElementMap().get(name).toString());
			//			
			//			List<String> componentIds = bioPax.getElementMap().get(name).getParams().get("pathwayComponent-id");
			//			System.out.println("componentIds = " + componentIds.size());
			//			for(String id: componentIds) {
			//				System.out.println("--> " + bioPax.getElementMap().get(id).toString());
			//				System.out.println("-----");
			//			}

			//System.out.println(bioPax.toString());
			System.out.println("\n------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayEntity(BioPax bioPax, String name, String indent) {
		System.out.println(indent);
		System.out.println(bioPax.getElementMap().get(name).toString());

		if (bioPax.getElementMap().get(name).getParams().containsKey("pathwayComponent-id")) {
			List<String> componentIds = bioPax.getElementMap().get(name).getParams().get("pathwayComponent-id");
			System.out.println("componentIds = " + componentIds.size());
			for(String id: componentIds) {
				displayEntity(bioPax, id, indent + "   *****");
			}
		}
	}

	public void displayEntityOLD(BioPax bioPax, String name, String indent) {
		System.out.println(indent);
		System.out.println(bioPax.getElementMap().get(name).toString());

		if (bioPax.getElementMap().get(name).getParams().containsKey("pathwayComponent-id")) {
			List<String> componentIds = bioPax.getElementMap().get(name).getParams().get("pathwayComponent-id");
			System.out.println("componentIds = " + componentIds.size());
			for(String id: componentIds) {
				displayEntity(bioPax, id, indent + "   *****");
			}
		}
	}

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
			/*			
			boolean ini = true;

			List<String> interactionList = bioPax.getInteractionList();
			System.out.println("number of interactions = " + interactionList.size());

			List<String> from, to;
			List<String> proteinFrom, proteinTo;

			Map<String,Integer> mapFrom = new HashMap<String,Integer>(); 
			Map<String,Integer> mapTo = new HashMap<String,Integer>(); 

			for(String key: interactionList) {
				bioPaxElement = mapElement.get(key);
				from = null;
				to = null;
				if (ini) {
					System.out.println(bioPaxElement.toString());
					ini = false;
				}

				if (bioPaxElement.getParams().containsKey("controller-id") && bioPaxElement.getParams().containsKey("controlled-id")) {
					from = bioPaxElement.getParams().get("controller-id");
					to = bioPaxElement.getParams().get("controlled-id");
				} else if (bioPaxElement.getParams().containsKey("left-id") && bioPaxElement.getParams().containsKey("rigth-id")) {
					from = bioPaxElement.getParams().get("left-id");
					to = bioPaxElement.getParams().get("right-id");					
				}

				if (from!=null && to!=null) {
					String msg = "";
					msg += bioPaxElement.getBioPaxClassName() + "\t\t: ";
					for(String item: from) {
						msg += item + "(" + mapElement.get(item).getBioPaxClassName() + "), ";
						if (!mapFrom.containsKey(mapElement.get(item).getBioPaxClassName())) {
							mapFrom.put(mapElement.get(item).getBioPaxClassName(), 0);
						}
						mapFrom.put(mapElement.get(item).getBioPaxClassName(), mapFrom.get(mapElement.get(item).getBioPaxClassName()) + 1);
					}
					msg += " >>>> ";
					for(String item: to) {
						msg += item + "(" + mapElement.get(item).getBioPaxClassName() + "), ";
						if (!mapTo.containsKey(mapElement.get(item).getBioPaxClassName())) {
							mapTo.put(mapElement.get(item).getBioPaxClassName(), 0);
						}
						mapTo.put(mapElement.get(item).getBioPaxClassName(), mapTo.get(mapElement.get(item).getBioPaxClassName()) + 1);
					}
					System.out.println(msg);
					//System.out.println(bioPaxElement.getBioPaxClassName() + ": " + ListUtils.toString(from) + " -> " + ListUtils.toString(to));
				}
			}

			System.out.println("From componets...");
			for(String key: mapFrom.keySet()) {
				System.out.println("\t" + key + "\t" + mapFrom.get(key));
			}

			System.out.println("To componets...");
			for(String key: mapTo.keySet()) {
				System.out.println("\t" + key + "\t" + mapTo.get(key));
			}
			 */			
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

//	@Test
	public void Test12() {
		String filename = "/mnt/commons/formats/reactome/Homo sapiens.owl";
		try {
			BioPaxParser parser = new BioPaxParser(filename);
			BioPax bioPax = parser.parse();
			
			String id = "Length_1_241_";
			System.out.println("id = " + id);
			
			BioPaxElement e = bioPax.getElementMap().get(id);
			if (e!=null) {
				System.out.println(e.toString());				
			} else {
				System.out.println("is BioPaxElement null ? " + (e==null));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		//		System.out.println("\n");		
		//
		//		String indent = "-----|";
		//		List<String> list = bioPax.getPhysicalEntityList();

	}


}
