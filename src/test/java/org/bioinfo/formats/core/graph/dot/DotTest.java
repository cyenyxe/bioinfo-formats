package org.bioinfo.formats.core.graph.dot;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class DotTest {

	@Test
	public void Test1() {
		Dot dot = new Dot("toto", true);
		dot.setAttribute(Dot.BGCOLOR, "white");
		dot.setAttribute(Dot.ORIENTATION, Dot.ORIENTATION_VALUES.landscape.name());
		
		Node node1 = new Node("node_1");
		node1.setAttribute(Node.LABEL, "Node #1");
		node1.setAttribute(Node.FILLCOLOR, "#CCFFFF");
		node1.setAttribute(Node.SHAPE, Node.SHAPE_VALUES.box3d.name());
		node1.setAttribute(Node.STYLE, Node.STYLE_VALUES.filled.name());
		dot.addNode(node1);
		
		Node node2 = new Node("node_2");
		node2.setAttribute(Node.LABEL, "Node #2");
		node2.setAttribute(Node.FILLCOLOR, "#CCFFCC");
		node2.setAttribute(Node.SHAPE, Node.SHAPE_VALUES.component.name());
		node2.setAttribute(Node.STYLE, Node.STYLE_VALUES.rounded.name() + "," + Node.STYLE_VALUES.filled.name());
		dot.addNode(node2);
		
		Edge edge = new Edge(node1, node2, true);
		edge.setAttribute(Edge.LABEL, "control");
		edge.setAttribute(Edge.ARROWHEAD, Edge.ARROWHEAD_VALUES.diamond.name());
		dot.addEdge(edge);
				
		try {
			dot.save(new File("/mnt/commons/test/formats/" + dot.getName() + ".dot"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
