package com.trains;

import org.junit.Test;

import static junit.framework.Assert.*;

public class TestGraph {

    @Test
    public void testAddNode() throws NodeDoesNotExistInGraphException {
        Graph graph = new Graph();
        assertEquals("New graph must not have nodes", 0, graph.getNodes().size());
        graph.addNode(new Node("A"));
        assertNotNull(graph.getNode("A"));
        assertEquals("Error adding node to a graph", 1, graph.getNodes().size());
    }

    @Test(expected = NodeDoesNotExistInGraphException.class)
    public void testNotExistingNode() throws NodeDoesNotExistInGraphException {
        Graph graph = new Graph();
        graph.addNode(new Node("A"));
        graph.getNode("B");
    }

    @Test
    public void testAddEdge() throws NodeDoesNotExistInGraphException {
        Graph graph = new Graph();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        assertEquals("New nodes must not have neighbours", 0, graph.getNode("A").getEdges().size());
        nodeA.addEdge(new Edge("A", "B", 100));
        assertEquals("Error on adding edges", 1, graph.getNode(nodeA.getId()).getEdges().size());
        assertNotNull("Error on adding edges", graph.getNode(nodeA.getId()).getEdgeTo(nodeB.getId()));
        assertEquals("Error on edge cost", 100, graph.getNode(nodeA.getId()).getEdgeTo(nodeB.getId()).getCost());
        assertTrue("When adding an edge nodes must became neighbours", nodeA.isNeighbour(nodeB));
        assertFalse("Adding edge is not bidirectional", nodeB.isNeighbour(nodeA));
        assertEquals("Adding an edge must not modify target node", 0, graph.getNode(nodeB.getId()).getEdges().size());
    }


}
