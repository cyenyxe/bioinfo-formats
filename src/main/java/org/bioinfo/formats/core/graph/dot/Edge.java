package org.bioinfo.formats.core.graph.dot;

import java.util.HashMap;
import java.util.Map;

public class Edge {
	
	private boolean directed = true;
	
	private String srcName = null;
	private String destName = null;
	
	private Node src = null;
	private Node dest = null;
	
	private Map<String, String> attrs = new HashMap<String, String>();

	public Edge(String src, String dest) {
		this(src, dest, true);
	}
	
	public Edge(String src, String dest, boolean directed) {
		this.directed = directed;
		this.srcName = src;
		this.destName = dest;
	}
	
	public Edge(String src, String dest, Map<String, String> attrs) {
		this(src, dest, true, attrs);
	}
	
	public Edge(String src, String dest, boolean directed, Map<String, String> attrs) {
		this.directed = directed;
		this.srcName = src;
		this.destName = dest;
		this.attrs = attrs;
	}
	
	public Edge(Node src, Node dest) {
		this(src, dest, true);
	}

	public Edge(Node src, Node dest, boolean directed) {
		this.directed = directed;
		this.src = src;
		this.dest = dest;
	}
	
	public Edge(Node src, Node dest, Map<String, String> attrs) {
		this(src, dest, true, attrs);
	}
	
	public Edge(Node src, Node dest, boolean directed, Map<String, String> attrs) {
		this.directed = directed;
		this.src = src;
		this.dest = dest;
		this.attrs = attrs;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(srcName).append(directed ? "->" : "--").append(destName);			
		if(attrs!=null && attrs.size()>0) {
			sb.append(" [");
			for(String key: attrs.keySet()) {
				if(key.equalsIgnoreCase("constraint") || key.equalsIgnoreCase("labelfloat")) {		
					sb.append(key).append(",");
				} else {
					sb.append(key.toLowerCase()).append("=\"").append(attrs.get(key)).append("\",");
				}
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append(" ]");
		}
		sb.append(";\n");
		
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
	
	public void setSource(Node src) {
		this.src = src;
		this.srcName = src.getName();
	}
	
	public Node getSource() {
		return src;
	}

	public void setDestination(Node dest) {
		this.dest = dest;
		this.destName = dest.getName();
	}
	
	public Node getDestination() {
		return dest;
	}
	
	public String getSrcName() {
		return srcName;
	}

	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}
	
}
