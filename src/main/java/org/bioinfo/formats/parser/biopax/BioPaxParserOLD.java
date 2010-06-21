package org.bioinfo.formats.parser.biopax;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class BioPaxParserOLD {

//    private HashMap<String,Element> rdfResources = new HashMap<String,Element>();
//    
//    private ArrayList<Element> geneList = new ArrayList<Element>();
//    private ArrayList<Element> pathwayList = new ArrayList<Element>();
//    private ArrayList<Element> interactionList = new ArrayList<Element>();
//    private ArrayList<Element> physicalEntityList = new ArrayList<Element>();
//    
//    private ArrayList<Element> ontologyList = new ArrayList<Element>();
////    private BioPaxConstants bioPaxConstants = new BioPaxConstants();
//    private ArrayList<String> errorList = new ArrayList<String>();
//    
//    private Document bioPaxDoc;
//    
//    private HashMap<String,List<String>> pathwayMembershipMap;
//    private HashSet<String> visitedNodeSet;
//    private HashSet<String> referenceSet;
//
//
//    /**
//     * Constructor.
//     *
//     * @param reader Reader Object.
//     * @throws IOException   Input/Output Error.
//     * @throws JDOMException XML Error.
//     */
//    public BioPaxParserOLD(Reader reader) throws IOException, JDOMException {
//        loadDocument(reader);
//    }
//
//    /**
//     * Gets HashMap of All RDF Resources, keyed by RDF ID.
//     *
//     * @return HashMap of All RDF Resources, keyed by RDF ID.
//     */
//    public HashMap<String,Element> getRdfResourceMap() {
//        return rdfResources;
//    }
//
//    /**
//     * Gets list of Pathway Resources.
//     *
//     * @return ArrayList of JDOM Element Objects.
//     */
//    public ArrayList<Element> getPathwayList() {
//        return pathwayList;
//    }
//
//    /**
//     * Gets List of Interaction Resources.
//     *
//     * @return ArrayList of JDOM Element Objects.
//     */
//    public ArrayList<Element> getInteractionList() {
//        return interactionList;
//    }
//
//    /**
//     * Gets List of Physical Entity Resources.
//     *
//     * @return ArrayList of JDOM Element Objects.
//     */
//    public ArrayList<Element> getPhysicalEntityList() {
//        return physicalEntityList;
//    }
//
//    /**
//     * Gets the HashMap of Pathway Membership.
//     *
//     * @return HashMap of Element RDF Ids (String) to an ArrayList of Pathway
//     *         RDF IDs (Strings).
//     */
//    public HashMap<String, List<String>> getPathwayMembershipMap() {
//        return pathwayMembershipMap;
//    }
//
//    /**
//     * Gets a List of all Pathways, Interactions, and Physical Entities.
//     *
//     * @return ArrayList of JDOM Element Objects.
//     */
//    public ArrayList<Element> getTopLevelComponentList() {
//        ArrayList<Element> list = new ArrayList<Element>();
//        list.addAll(pathwayList);
//        list.addAll(interactionList);
//        list.addAll(physicalEntityList);
//        return list;
//    }
//
//    /**
//     * Gets List of Errors.
//     *
//     * @return ArrayList of String Objects.
//     */
//    public ArrayList<String> getErrorList() {
//        return errorList;
//    }
//
//    /**
//     * Gets the Root Element of the BioPAX Tree.
//     *
//     * @return JDOM Element Object.
//     */
//    public Element getRootElement() {
//        return this.bioPaxDoc.getRootElement();
//    }
//
//    /**
//     * Gets the RDF ID / About ID.
//     * @param e Element Object.
//     * @return  RDF ID.
//     */
//    public static String extractRdfId(Element e) {
//        //  First, try to obtain RDF ID
//        String id = e.getAttributeValue(BioPaxConstants.RDF_ID_ATTRIBUTE, BioPaxConstants.RDF_NAMESPACE);
//
//        //  If RDF ID Fails, try using the RDF About Attribute
//        if (id == null) {
//            id = e.getAttributeValue(BioPaxConstants.RDF_ABOUT_ATTRIBUTE, BioPaxConstants.RDF_NAMESPACE);
//        }
//        return id;
//    }
//
//    /**
//     * Gets the RDF ID / About Attribute.
//     * @param e Element Object.
//     * @return  RDF ID.
//     */
//    public static Attribute extractRdfIdAttribute(Element e) {
//        //  First, try to obtain RDF ID
//        Attribute idAttribute = e.getAttribute(BioPaxConstants.RDF_ID_ATTRIBUTE, BioPaxConstants.RDF_NAMESPACE);
//
//        if (idAttribute == null) {
//            idAttribute = e.getAttribute(BioPaxConstants.RDF_ABOUT_ATTRIBUTE, BioPaxConstants.RDF_NAMESPACE);
//        }
//        return idAttribute;
//    }
//
//    /**
//     * Loads the Specified Document.
//     */
//    private void loadDocument(Reader reader) throws JDOMException, IOException {
//        pathwayMembershipMap = new HashMap<String,List<String>>();
//        referenceSet = new HashSet<String>();
//
//        //  Read in File via JDOM SAX Builder
//        SAXBuilder builder = new SAXBuilder();
//        bioPaxDoc = builder.build(reader);
//
//        //  Get Root Element
//        Element root = bioPaxDoc.getRootElement();
//
//        //  First Step:  Inspect Tree to categorize all RDF Resources
//        categorizeResources(root, null);
//
//        //  Second Step:  Validate that all RDF links point to actual
//        //  RDF Resources, defined in the document.
//        validateResourceLinks(root);
//
//        //  Third Step:  Determine Pathway Memberhip
//        determinePathwayMembership();
//    }
//
//    private void determinePathwayMembership() {
//        //  First, determine top-level pathways
//        ArrayList<Element> topLevelPathways = new ArrayList<Element>();
//        for (int i = 0; i < pathwayList.size(); i++) {
//            Element pathway = (Element) pathwayList.get(i);
//            Attribute idAttribute = extractRdfIdAttribute(pathway);
//            if (idAttribute != null) {
//                String rdfId = idAttribute.getValue();
//
//                //  If nothing references this pathway, it is considered
//                //  a "top-level" pathway.
//                if (!referenceSet.contains(rdfId)) {
//                    topLevelPathways.add(pathway);
//                }
//            }
//        }
//
//        //  Then, iterate through all top-level pathways.
//        for (int i = 0; i < topLevelPathways.size(); i++) {
//            visitedNodeSet = new HashSet<String>();
//            Element pathway = (Element) topLevelPathways.get(i);
//            traversePathway(pathway, pathway);
//        }
//    }
//
//    /**
//     * Recursively Traverse an Entire Pathway, in order to determine
//     * pathway membership of all elements in the document.
//     */
//    private void traversePathway(Element e, Element rootLevelPathway) {
//        boolean keepTraversingTree = true;
//        if (e != rootLevelPathway) {
//            //  Get an RDF ID Attribute, if there is one
//            Attribute idAttribute = extractRdfIdAttribute(e);
//
//            //  Get a pointer to an RDF resource, if there is one.
//            Attribute pointerAttribute = e.getAttribute(BioPaxConstants.RDF_RESOURCE_ATTRIBUTE, BioPaxConstants.RDF_NAMESPACE);
//
//            if (idAttribute != null) {
//                //  Case 1:  The element has an RDF ID attribute.
//                this.setPathwayMembership(e, rootLevelPathway);
//            } else if (pointerAttribute != null) {
//                //  Case 2:  The element has an RDF Resource/Pointer Attribute
//                String uri = removeHashMark(pointerAttribute.getValue());
//                Element referencedResource = (Element) rdfResources.get(uri);
//                if (referencedResource != null) {
//                    if (visitedNodeSet.contains(uri)) {
//                        //  If we have already been here, stop traversing.
//                        //  Prevents Circular References.
//                        keepTraversingTree = false;
//                    } else {
//                        setPathwayMembership(referencedResource,
//                                rootLevelPathway);
//                        visitedNodeSet.add(uri);
//
//                        //  Now, keep walking from the referenced resource
//                        e = referencedResource;
//                    }
//                }
//            }
//        }
//
//        //  Traverse through all children.
//        if (keepTraversingTree) {
//            List<Element> children = (List<Element>) e.getChildren();
//            for (int i = 0; i < children.size(); i++) {
//                Element child = (Element) children.get(i);
//                traversePathway(child, rootLevelPathway);
//            }
//        }
//    }
//
//    /**
//     * Categorizes the document into top-level components:  pathways,
//     * interactions, and physical entities.
//     */
//    private void categorizeResources(Element e, Element rootLevelPathway) {
//
//        //  First, separate out any OWL Specific Elements
//        String namespaceUri = e.getNamespaceURI();
//        if (namespaceUri.equals(BioPaxConstants.OWL_NAMESPACE_URI)) {
//            ontologyList.add(e);
//            return;
//        }
//
//        //  Get an RDF ID Attribute, if available
//        Attribute idAttribute = extractRdfIdAttribute(e);
//
//        //  Set an RDF Pointer Attribute, if available
//        Attribute pointerAttribute = e.getAttribute(BioPaxConstants.RDF_RESOURCE_ATTRIBUTE, BioPaxConstants.RDF_NAMESPACE);
//
//        if (idAttribute != null) {
//            //  Store element to hashmap, keyed by RDF ID
//            if (rdfResources.containsKey(idAttribute.getValue())) {
//                errorList.add(new String("Element:  " + e
//                        + " declares RDF ID:  " + idAttribute.getValue()
//                        + ", but a resource with this ID already exists."));
//            } else {
//                rdfResources.put(idAttribute.getValue(), e);
//            }
//
//            // If this is not a top-level element, it is implicitly
//            // referenced via the XML hierarchy.  Therefore, add it to the
//            // referenceSet
//            Element parent = (Element) e.getParent();
//            if (!parent.getName().equals(BioPaxConstants.RDF_ROOT_NAME)) {
//                referenceSet.add(idAttribute.getValue());
//            }
//        } else if (pointerAttribute != null) {
//            // If we point to something, mark it in the referenceSet
//            String uri = removeHashMark(pointerAttribute.getValue());
//            referenceSet.add(uri);
//        }
//
//        //  Categorize into separate bins
//        String name = e.getName();
//        if (BioPaxConstants.isPathway(name)) {
//            pathwayList.add(e);
//        } else if (BioPaxConstants.isInteraction((name))) {
//            interactionList.add(e);
//        } else if (BioPaxConstants.isPhysicalEntity(name)) {
//            physicalEntityList.add(e);
//        } else if (BioPaxConstants.isGene(name)) {
//            geneList.add(e);
//        }
//
//        //  Traverse through all children of current element
//        List children = e.getChildren();
//        for (int i = 0; i < children.size(); i++) {
//            Element child = (Element) children.get(i);
//            categorizeResources(child, rootLevelPathway);
//        }
//    }
//
//    private void setPathwayMembership(Element e, Element rootLevelPathway) {
//        if (e != null && rootLevelPathway != null) {
//            Attribute elementIdAttribute = extractRdfIdAttribute(e);
//            Attribute pathwayIdAttribute = extractRdfIdAttribute
//                    (rootLevelPathway);
//            if (elementIdAttribute != null && pathwayIdAttribute != null) {
//                String elementId = elementIdAttribute.getValue();
//                String pathwayId = pathwayIdAttribute.getValue();
//
//                //  Entity can be part of several pathways.
//                if (pathwayMembershipMap.containsKey(elementId)) {
//                    List<String> list = pathwayMembershipMap.get(elementId);
//                    if (!list.contains(pathwayId)) {
//                        list.add(pathwayId);
//                    }
//                } else {
//                    ArrayList list = new ArrayList();
//                    list.add(pathwayId);
//                    pathwayMembershipMap.put(elementId, list);
//                }
//            }
//        }
//    }
//
//    /**
//     * Validates that all RDF Links are valid.
//     */
//    private void validateResourceLinks(Element e) {
//        //  Get an RDF Resource Attribute, if available
//        Attribute resourceAttribute = e.getAttribute(BioPaxConstants.RDF_RESOURCE_ATTRIBUTE, BioPaxConstants.RDF_NAMESPACE);
//
//        //  Ignore all OWL Specific Elements
//        String namespaceUri = e.getNamespaceURI();
//        if (namespaceUri.equals(BioPaxConstants.OWL_NAMESPACE_URI)) {
//            return;
//        }
//
//        if (resourceAttribute != null) {
//            String key = removeHashMark(resourceAttribute.getValue());
//            if (!rdfResources.containsKey(key)) {
//                errorList.add(new String("Element:  " + e + " references:  " + key + ", but no such resource " + "exists in document."));
//            }
//        }
//
//        //  Traverse through all children of current element
//        List children = e.getChildren();
//        for (int i = 0; i < children.size(); i++) {
//            Element child = (Element) children.get(i);
//            validateResourceLinks(child);
//        }
//    }
//
//    private String removeHashMark(String value) {
//    	if (value.startsWith("#")) {
//    		return value.substring(1);
//    	}
//    	return value;
//    }
}
