package com.trains;

import com.trains.model.Edge;
import com.trains.model.Node;
import com.trains.model.NodeDoesNotExistInGraphException;

import java.util.HashMap;

public class ShortestRouteCalculator {
    private final RailRoad railRoad;

    public ShortestRouteCalculator(RailRoad railRoad) {
        this.railRoad = railRoad;
    }

    /**
     * Calculates the shortest route between two towns.
     *
     * @param nodeFromId The id of the source town
     * @param nodeToId   The id of the target town
     * @return the shortest distance.
     */
    public String calculateShortestRoute(String nodeFromId, String nodeToId) throws NodeDoesNotExistInGraphException {
        Node nodeFrom = railRoad.getGraph().getNode(nodeFromId);
        Node nodeTo = railRoad.getGraph().getNode(nodeToId);

        HashMap<String, Integer> distances = new HashMap<String, Integer>();
        HashMap<String, Boolean> seen = new HashMap<String, Boolean>();

        for (String nodeId : railRoad.getGraph().getNodes().keySet()) {
            distances.put(nodeId, -1);
            seen.put(nodeId, false);
        }
        distances.put(nodeFromId, 0);

        Node actualNode = nodeFrom;
        while (actualNode != null) {
            for (Edge edge : actualNode.getEdges().values()) {
                Node neighbour = railRoad.getGraph().getNode(edge.getToId());
                String neighbourId = neighbour.getId();
                Integer newDistance = distances.get(actualNode.getId()) + edge.getCost();
                if (!seen.get(neighbourId) && distances.get(neighbourId).equals(new Integer(-1)) || distances.get(neighbourId) > newDistance) {
                    distances.put(neighbourId, newDistance);
                }
            }
            seen.put(actualNode.getId(), true);
            actualNode = getNextNode(distances, seen);
        }

        //If fromId and toId are the same
        if (nodeFromId.equals(nodeTo.getId())) {
            Integer min = -1;
            Node neighbourNode = null;
            for (Node node : railRoad.getGraph().getNodes().values()) {
                if (node.isNeighbour(railRoad.getGraph().getNode(nodeFromId)) && distances.get(node.getId()) != -1) {
                    if (min == -1 || min > node.getEdgeTo(nodeFromId).getCost()) {
                        min = node.getEdgeTo(nodeFromId).getCost();
                        neighbourNode = node;
                    }
                }
            }
            if (min != -1) {
                Integer result = new Integer(min + distances.get(neighbourNode.getId()));
                return result.toString();
            } else {
                return "NO ROUTE";
            }
        }

        return distances.get(nodeToId).toString();
    }

    private Node getNextNode(HashMap<String, Integer> distances, HashMap<String, Boolean> seen) throws NodeDoesNotExistInGraphException {
          Integer dist = -1;
          Node resultNode = null;
          for (String nodesId : railRoad.getGraph().getNodes().keySet()) {
              if (!seen.get(nodesId) && !distances.get(nodesId).equals(new Integer(-1))) {
                  if (dist.equals(new Integer(-1)) || dist > distances.get(nodesId)) {
                      dist = distances.get(nodesId);
                      resultNode = railRoad.getGraph().getNode(nodesId);
                  }
              }
          }
          return resultNode;
      }
}