package com.trains;

import java.util.HashMap;

public class Graph {

    private HashMap<String, Node> nodes;

    public Graph() {
        nodes = new HashMap<String, Node>();
    }

    public void addNode(Node node) {
        this.nodes.put(node.getId(), node);
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }

    public Node getNode(String nodeId) {
        return nodes.get(nodeId);
    }
}
