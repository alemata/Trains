package com.trains;

import java.util.HashMap;

public class Node {

    private String id;
    private HashMap<String, Edge> edges;

    public Node(String id) {
        this.id = id;
        edges = new HashMap<String, Edge>();
    }

    public void addEdge(Edge edge) {
        this.edges.put(edge.getToId(), edge);
    }

    public Edge getEdgeTo(String nodeId) {
        return this.edges.get(nodeId);
    }

    public boolean isNeighbour(String nodeId) {
        return this.edges.get(nodeId) != null;
    }

    public HashMap<String, Edge> getEdges() {
        return edges;
    }

    public String getId() {
        return id;
    }
}
