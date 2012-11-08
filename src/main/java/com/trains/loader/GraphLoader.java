package com.trains.loader;

import com.trains.model.Edge;
import com.trains.model.Graph;
import com.trains.model.Node;
import com.trains.model.NodeDoesNotExistInGraphException;

public class GraphLoader {

    public static Graph readGraphFromString(String graphString) throws InconsistentGraphException {
        String[] data = graphString.split(",");
        Graph graph = new Graph();
        for (String nodeString : data) {
            String[] nodeDataSplit = nodeString.split("/");
            String nodeId = nodeDataSplit[0];
            Node node = new Node(nodeId);
            for (int j = 1; j < nodeDataSplit.length - 1; j += 2) {
                String neighborId = nodeDataSplit[j];
                Integer neighborCost = Integer.parseInt(nodeDataSplit[j + 1]);
                node.addEdge(new Edge(nodeId, neighborId, neighborCost));
            }
            graph.addNode(node);
        }
        validateGraph(graph);
        return graph;
    }

    private static void validateGraph(Graph graph) throws InconsistentGraphException {
        for (Node node : graph.getNodes().values()) {
            for (Edge edge : node.getEdges().values()) {
                try {
                    graph.getNode(edge.getToId());
                } catch (NodeDoesNotExistInGraphException e) {
                    throw new InconsistentGraphException("The graph is inconsistent", e);
                }
            }
        }
    }
}


