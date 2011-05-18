package org.bioinfo.formats.core.graph.dot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bioinfo.commons.io.utils.IOUtils;

public class Dot {

	private String name;
	private boolean directed = true;
	private Map<String, Node> nodes = new HashMap<String, Node>();
	private List<Edge> edges = new ArrayList<Edge>();
	
	private Map<String, String> attrs = new HashMap<String, String>();
	
	public Dot(String name) {
		this.name = name;
	}

	public Dot(String name, boolean directed) {
		this.name = name;
		this.directed = directed;
	}
	
	public Dot(String name, boolean directed, Map<String, String> attrs) {
		this.name = name;
		this.directed = directed;
		this.attrs = attrs;
	}
	
	public void addNode(Node node) {
		nodes.put(node.getName(), node);
	}
	
	public void addEdge(Edge edge) {
		edges.add(edge);
		if (!nodes.containsKey(edge.getSrcName())) {
			nodes.put(edge.getSrcName(), edge.getSource());
		}
		if (!nodes.containsKey(edge.getDestName())) {
			nodes.put(edge.getDestName(), edge.getDestination());
		}
	}
	
	public void save(String filename) throws IOException {
		IOUtils.write(filename, toString());
	}
	
	public void save(File file) throws IOException {
		IOUtils.write(file, toString());
	}	

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(directed ? "digraph " : "graph \"").append(name).append("\" {\n");			
		if(attrs!=null && attrs.size()>0) {
			for(String key: attrs.keySet()) {
				sb.append("\t");
				if(key.equalsIgnoreCase("center") || key.equalsIgnoreCase("compound") || key.equalsIgnoreCase("concentrate")) {		
					sb.append(key).append(";\n");
				} else {
					sb.append(key.toLowerCase()).append("=\"").append(attrs.get(key)).append("\";\n");
				}
			}
		}
		
		for(String key: nodes.keySet()) {
			sb.append("\t").append(nodes.get(key).toString());			
		}
		
		for(Edge edge: edges) {
			sb.append("\t").append(edge.toString());			
		}
		
		sb.append("}\n");
		
		return sb.toString();
	}
	
	public void setAttribute(String key, String value) {
		attrs.put(key, value);
	}
		
	public void setAttributes(Map<String, String> attrs) {
		this.attrs = attrs;
	}
	
	public Map<String, String> getAttributes() {
		return attrs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
