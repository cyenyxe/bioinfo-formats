package org.bioinfo.formats.core.graph.dot;

import java.util.HashMap;
import java.util.Map;

public class Node {
	
	private String name = null;
	private Map<String, String> attrs = new HashMap<String, String>();

	public Node(String name) {
		this.name = name;
	}
	
	public Node(String name, Map<String, String> attrs) {
		this.name = name;
		this.attrs = attrs;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if(attrs!=null && attrs.size()>0) {
			sb.append(" [");
			for(String key: attrs.keySet()) {
				if(key.equalsIgnoreCase("fixedsize") || key.equalsIgnoreCase("regular")) {		
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}		
}
