package org.bioinfo.formats.core.graph.dot;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class DotTest {

	@Test
	public void Test1() {
		Dot dot = new Dot("toto", true);
		
		Node node1 = new Node("node_1");
		node1.setAttribute("label", "Node #1");
		node1.setAttribute("fillcolor", "red");
		node1.setAttribute("shape", "box");
		node1.setAttribute("style", "filled");
		dot.addNode(node1);
		
		Node node2 = new Node("node_2");
		node2.setAttribute("label", "Node #2");
		node2.setAttribute("fillcolor", "blue");
		node2.setAttribute("shape", "box");
		node2.setAttribute("style", "rounded,filled");
		dot.addNode(node2);
		
		Edge edge = new Edge(node1, node2, true);
		edge.setAttribute("label", "control");
		dot.addEdge(edge);
				
		try {
			dot.save(new File("/mnt/commons/test/formats/" + dot.getName() + ".dot"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
