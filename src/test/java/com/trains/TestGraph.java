package com.trains;

import org.junit.Test;

import static junit.framework.Assert.*;

public class TestGraph {


    public TestGraph() {
    }

    @Test
    public void testAddNode() {
        Graph graph = new Graph();
        assertEquals("New graph must not have nodes", 0, graph.getNodes().size());
        assertNull(graph.getNode("A"));
        graph.addNode(new Node("A"));
        assertNotNull(graph.getNode("A"));
        assertEquals("Error adding node to a graph", 1, graph.getNodes().size());
    }

    @Test
    public void testAddEdge() {
        Graph graph = new Graph();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        assertEquals("New nodes must not have neighbours", 0, graph.getNode("A").getEdges().size());
        nodeA.addEdge(new Edge("A", "B", 100));
        assertEquals("Error on adding edges", 1, graph.getNode(nodeA.getId()).getEdges().size());
        assertNotNull("Error on adding edges", graph.getNode(nodeA.getId()).getEdgeTo(nodeB.getId()));
        assertEquals("Error on adding edges", 100, graph.getNode(nodeA.getId()).getEdgeTo(nodeB.getId()).getCost());
        assertTrue("Error on adding edges", nodeA.isNeighbour(nodeB.getId()));
        assertFalse("Error unidirectional edge", nodeB.isNeighbour(nodeA.getId()));
        assertEquals("Error on adding edges", 0, graph.getNode(nodeB.getId()).getEdges().size());
    }


}
