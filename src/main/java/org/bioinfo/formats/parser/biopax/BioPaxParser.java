package org.bioinfo.formats.parser.biopax;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

//import org.jdom.Attribute;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.JDOMException;
//import org.jdom.Namespace;
//import org.jdom.Text;
//import org.jdom.input.SAXBuilder;

public class BioPaxParser extends DefaultHandler {

	private String filename;

	private String currEntityId = null;
	private String currEntityClass = null;
	private BioPaxElement currBioPaxElement = null;
	
	private String paramId = null;
	private String paramName = null;
	private StringBuilder paramValue = new StringBuilder();

	private BioPax bioPax;
	
	//	private Document bioPaxDoc;
	//	private Namespace nsRdf = Namespace.getNamespace("rdf","http://www.w3.org/1999/02/22-rdf-syntax-ns#");

	public BioPaxParser(String filename) {
		super();
		this.filename = filename;
	}

	public BioPax parse() throws IOException, SAXException {
		return parse(filename);
	}

	public BioPax parse(String filename) throws IOException, SAXException {
		bioPax = new BioPax();
		File file = new File(filename);
		if (!file.exists()) {
			throw new IOException("File " + filename + " does not exist");
		}

		XMLReader xr = XMLReaderFactory.createXMLReader();
		xr.setContentHandler(this);
		xr.parse(new InputSource(new FileReader(filename)));

		return bioPax;
	}


	////////////////////////////////////////////////////////////////////
	// Event handlers.
	////////////////////////////////////////////////////////////////////


	public void startDocument() {
		System.out.println("Start document");
	}


	public void endDocument() {
		System.out.println("End document");
	}


	public void startElement(String uri, String name, String qName, Attributes atts) {
		String id = null;
		String resource = null;

		//System.out.println("--> startElement: " + name);

		String entityClass = BioPaxConstants.getEntityClass(name); 
		if (entityClass!=null) {

			//System.out.println("name = " + name + ", entity class = " + entityClass);

			if (currEntityId==null) {
				
				if (atts!=null && atts.getLength()>0) {
					for(int i=0 ; i<atts.getLength() ; i++) {
						if ("ID".equalsIgnoreCase(atts.getLocalName(i))) {
							id = atts.getValue(i);atts.getLocalName(i);
							break;
						}
					}
					if (id!=null) {
						currEntityId = id;
						currEntityClass = entityClass;
						currBioPaxElement = new BioPaxElement(id, name);
						//System.out.println("  attribute " + i + ": " + atts.getLocalName(i) + " = " + atts.getValue(i));
					}
				}
			}
		} else {
			if (currEntityId!=null) {
				if (atts!=null && atts.getLength()>0) {
					for(int i=0 ; i<atts.getLength() ; i++) {
						if ("resource".equalsIgnoreCase(atts.getLocalName(i))) {
							resource = atts.getValue(i);
							if (resource.startsWith("#")) {
								resource = resource.substring(1);
							}
							break;
						}
					}
				}
				paramId = resource;
				paramName = name;
				paramValue.setLength(0);
			}
		}

		//System.out.println("Start element, (name, qName) = (" + name + ", " + qName + ")");
		//		if ("".equals (uri))
		//			System.out.println("Start element: " + qName);
		//		else
		//			System.out.println("Start element: {" + uri + "}" + name);
	}


	public void endElement(String uri, String name, String qName)	{
		
		//System.out.println("<-- endElement: " + name);
		
		if (currBioPaxElement!=null && currBioPaxElement.getBioPaxClassName()!=null) {
			if (currBioPaxElement.getBioPaxClassName().equalsIgnoreCase(name)) {
				
				bioPax.addEntity(currEntityId, currEntityClass, currBioPaxElement);
				//System.out.println("entity: " + currBioPaxElement.getBioPaxClassName() + ", class = " + currEntityClass + ", id = " + currEntityId);
				
				currEntityId = null;
				currEntityClass = null;
				currBioPaxElement = null;
				
			} else if (paramName.equalsIgnoreCase(name)) {
			
				if (paramId!=null) {
					currBioPaxElement.put(name + "-id", paramId);	
				}
				String aux = paramValue.toString();
				if (aux!=null && aux.trim().length()>0) {
					currBioPaxElement.put(name, aux.trim());
				}
				
				paramId = null;
				paramName = null;
				paramValue.setLength(0);				
			}
		}
	}


	public void characters(char ch[], int start, int length) {
		if (currEntityId!=null && paramName!=null) {
		//System.out.print("Characters: ");
			for (int i = start; i < start + length; i++) {
				paramValue.append(ch[i]);
			}
		}
			
			//			switch (ch[i]) {
			//			case '\\':
			//				System.out.print("\\\\");
			//				break;
			//			case '"':
			//				System.out.print("\\\"");
			//				break;
			//			case '\n':
			//				System.out.print("\\n");
			//				break;
			//			case '\r':
			//				System.out.print("\\r");
			//				break;
			//			case '\t':
			//				System.out.print("\\t");
			//				break;
			//			default:
			//System.out.print(ch[i]);
			//				break;
			//			}
//		}
//		System.out.print("\n");
	}
















	//        // Get an instance of the parser
	//        Parser parser = (Parser) new SAXParser();
	//
	//        // Set Handlers in the parser
	//        parser.setDocumentHandler(sample);
	//        parser.setEntityResolver(sample);
	//        parser.setDTDHandler(sample);
	//        parser.setErrorHandler(sample);
	//
	//        // Convert file to URL and parse
	//        try {
	//            parser.parse(fileToURL(new File(argv[0])).toString());
	//        } catch (SAXParseException e) {
	//            System.out.println(e.getMessage());
	//        } catch (SAXException e) {
	//            System.out.println(e.getMessage());
	//        }
	//        















	//		
	//		
	//		BioPax bioPax = new  BioPax();
	//
	//		//  Read in File via JDOM SAX Builder
	//		SAXBuilder builder = new SAXBuilder();
	//		bioPaxDoc = builder.build(filename);
	//
	//		System.out.println(filename + " is well-formed.");
	//
	//		//		//  Get Root Element
	//		//		Element root = bioPaxDoc.getRootElement();
	//		//	      Document doc = (Document) o;
	//		List children = bioPaxDoc.getContent();
	//		Iterator iterator = children.iterator();
	//		while (iterator.hasNext()) {
	//			browseNodes(iterator.next(), bioPax);
	//		}
	//
	//		return bioPax;
	//	}
	//
	//	private void browseNodes(Object o, BioPax bioPax) {
	//
	//		if (o instanceof Element) {
	//			Element element = (Element) o;
	//			List<Attribute> attrs = (List<Attribute>) element.getAttributes();
	//			if (attrs!=null && attrs.size()>0) {
	//				for(Attribute attr: attrs) {
	//					if ("ID".equalsIgnoreCase(attr.getName())) {
	//						bioPax.getElementMap().put(attr.getValue(), element);
	//						System.out.println("> " + element.getName() + ", id = " + attr.getValue());
	//					}
	//				}
	//			}
	//
	//			List children = element.getContent();
	//			Iterator iterator = children.iterator();
	//			while (iterator.hasNext()) {
	//				browseNodes(iterator.next(), bioPax);
	//			}
	//		}
	//		else if (o instanceof Text) {
	//			//System.out.println("Text, " +  " text = " + ((Text) o).getTextTrim() + ", value = " + ((Text) o).getValue());
	//		}
	//	}
}
