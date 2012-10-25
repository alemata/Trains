package com.trains;

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
                if(graph.getNode(edge.getToId()) == null){
                    throw new InconsistentGraphException("The graph is inconsistent");
                }
            }
        }
    }
}
