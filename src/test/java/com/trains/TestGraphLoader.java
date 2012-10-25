package com.trains;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class TestGraphLoader {

    private static final String GRAPH_STRING = "A/B/5/D/5/E/7,B/C/4,C/D/8/E/2,D/C/8/E/6,E/B/3";
    private static final String GRAPH_STRING2 = "A/B/5/C/8/I/9,B/C/5/A/23/F/14/J/10,C/D/5,D/E/5/B/100/G/3,E,F/G/5/A/32,G/B/10/H/10,H/I/5,I/A/5/H/3/B/44/C/2/E/12/G/20";

    public TestGraphLoader() {
    }

    @Test
    public void testLoader1() throws Exception {
        Graph graph = GraphLoader.readGraphFromString(GRAPH_STRING);
        assertEquals("Error in number of nodes", 5, graph.getNodes().size());
        assertEquals("Error reading neighbours", 3, graph.getNode("A").getEdges().size());
        assertEquals("Error reading neighbours", 1, graph.getNode("B").getEdges().size());
        assertEquals("Error reading neighbours", 2, graph.getNode("C").getEdges().size());
        assertEquals("Error reading neighbours", 2, graph.getNode("D").getEdges().size());
        assertEquals("Error reading neighbours", 1, graph.getNode("E").getEdges().size());
        assertNotNull("B must be a neighbour of A", graph.getNode("A").getEdgeTo("B"));
        assertNotNull("D must be a neighbour of A", graph.getNode("A").getEdgeTo("D"));
        assertNotNull("E must be a neighbour of A", graph.getNode("A").getEdgeTo("E"));
        assertEquals("Error with weight", 5, graph.getNode("A").getEdgeTo("B").getCost());
        assertEquals("Error with weight", 5, graph.getNode("A").getEdgeTo("D").getCost());
        assertEquals("Error with weight", 7 , graph.getNode("A").getEdgeTo("E").getCost());
    }

    @Test
    public void testLoader2() throws Exception {
        Graph graph = GraphLoader.readGraphFromString(GRAPH_STRING2);
        assertEquals("Error in number of nodes", 9, graph.getNodes().size());
        assertEquals("Error reading neighbours", 3, graph.getNode("A").getEdges().size());
        assertEquals("Error reading neighbours", 4, graph.getNode("B").getEdges().size());
        assertEquals("Error reading neighbours", 1, graph.getNode("C").getEdges().size());
        assertEquals("Error reading neighbours", 3, graph.getNode("D").getEdges().size());
        assertEquals("Error reading neighbours", 0, graph.getNode("E").getEdges().size());
        assertEquals("Error reading neighbours", 2, graph.getNode("F").getEdges().size());
        assertEquals("Error reading neighbours", 2, graph.getNode("G").getEdges().size());
        assertEquals("Error reading neighbours", 1, graph.getNode("H").getEdges().size());
        assertEquals("Error reading neighbours", 6, graph.getNode("I").getEdges().size());
        assertNotNull("G must be a neighbour of F", graph.getNode("F").getEdgeTo("G"));
        assertNotNull("A must be a neighbour of F", graph.getNode("F").getEdgeTo("A"));
        assertEquals("Error with weight", 5, graph.getNode("B").getEdgeTo("C").getCost());
        assertEquals("Error with weight", 23, graph.getNode("B").getEdgeTo("A").getCost());
        assertEquals("Error with weight", 14, graph.getNode("B").getEdgeTo("F").getCost());
        assertEquals("Error with weight", 10 , graph.getNode("B").getEdgeTo("J").getCost());
    }
}
