package com.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RailRoad {

    private static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";

    private Graph graph;

    public RailRoad() {
    }

    public RailRoad(Graph graph) {
        this.graph = graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    /**
     * Calculates the distance for a given route
     *
     * @param route the route to which calculate the distance
     * @return the distance of the route.
     */
    public String calculateRouteDistance(String route) throws NodeDoesNotExistInGraphException {
        Integer result = 0;
        String[] routeSplit = route.split("-");
        Node startingNode = graph.getNode(routeSplit[0]);

        Node actualNode = startingNode;
        for (int i = 1; i < routeSplit.length; i++) {
            if (actualNode.isNeighbour(graph.getNode(routeSplit[i]))) {
                result += actualNode.getEdgeTo(routeSplit[i]).getCost();
                actualNode = graph.getNode(routeSplit[i]);
            } else {
                return NO_SUCH_ROUTE;
            }
        }
        if (result == -1) {
            return NO_SUCH_ROUTE;
        } else {
            return result.toString();
        }
    }

    /**
     * Calculates the shortest route between two towns.
     *
     * @param nodeFromId The id of the source town
     * @param nodeToId   The id of the target town
     * @return the shortest distance.
     */
    public String calculateShortestRoute(String nodeFromId, String nodeToId) throws NodeDoesNotExistInGraphException {
        Node nodeFrom = graph.getNode(nodeFromId);

        HashMap<String, Integer> distances = new HashMap<String, Integer>();
        HashMap<String, Boolean> seen = new HashMap<String, Boolean>();

        for (String nodeId : graph.getNodes().keySet()) {
            distances.put(nodeId, -1);
            seen.put(nodeId, false);
        }
        distances.put(nodeFromId, 0);

        Node actualNode = nodeFrom;
        while (actualNode != null) {
            for (Edge edge : actualNode.getEdges().values()) {
                Node neighbour = graph.getNode(edge.getToId());
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
        if (nodeFromId.equals(nodeToId)) {
            Integer min = -1;
            Node neighbourNode = null;
            for (Node node : graph.getNodes().values()) {
                if (node.isNeighbour(graph.getNode(nodeFromId)) && distances.get(node.getId()) != -1) {
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
        for (String nodesId : graph.getNodes().keySet()) {
            if (!seen.get(nodesId) && !distances.get(nodesId).equals(new Integer(-1))) {
                if (dist.equals(new Integer(-1)) || dist > distances.get(nodesId)) {
                    dist = distances.get(nodesId);
                    resultNode = graph.getNode(nodesId);
                }
            }
        }
        return resultNode;
    }

    /**
     * Calculates the number of trips between two town with a maximum number of steps
     *
     * @param nodeFromId The source town Id
     * @param nodeToId   The target town Id
     * @param steps      The number of maximum steps
     * @return The number of trips.
     */
    public Integer calculateNumberOfTripsWithMaxSteps(String nodeFromId, String nodeToId, Integer steps) throws NodeDoesNotExistInGraphException {
        return calculateNumberOfTripsWithSteps(nodeFromId, nodeToId, steps, false);
    }

    /**
     * Calculates the number of trips between two town with an exact number of steps
     *
     * @param nodeFromId The source town Id
     * @param nodeToId   The target town Id
     * @param steps      The number steps
     * @return The numbers of trips with exact steps.
     */
    public Integer calculateNumberOfTripsWithExactSteps(String nodeFromId, String nodeToId, Integer steps) throws NodeDoesNotExistInGraphException {
        return calculateNumberOfTripsWithSteps(nodeFromId, nodeToId, steps, true);
    }

    private Integer calculateNumberOfTripsWithSteps(String nodeFromId, String nodeToId, Integer steps, Boolean mustBeExact) throws NodeDoesNotExistInGraphException {
        Integer result = 0;
        ArrayList<String> possibleRoutes = new ArrayList<String>();
        ArrayList<String> newPossibleRoutes;
        possibleRoutes.add(nodeFromId);
        while (steps-- > 0) {
            newPossibleRoutes = new ArrayList<String>();
            for (String possibleRoute : possibleRoutes) {
                Node lastNode = graph.getNode(String.valueOf(possibleRoute.charAt(possibleRoute.length() - 1)));
                for (Edge edge : lastNode.getEdges().values()) {
                    String newPossibleRoute = possibleRoute + "-" + edge.getToId();
                    newPossibleRoutes.add(newPossibleRoute);
                    if (!mustBeExact && newPossibleRoute.endsWith(nodeToId)) {
                        result++;
                    }
                }
            }
            possibleRoutes = newPossibleRoutes;
        }
        if (mustBeExact) {
            for (String possibleRoute : possibleRoutes) {
                if (possibleRoute.endsWith(nodeToId)) {
                    result++;
                }
            }
        }
        return result;
    }


    /**
     * Calculates the number of trips between two town with a maximum distance number.
     *
     * @param nodeFromId The source town Id
     * @param nodeToId   The target town Id
     * @param distance   The maximum distance
     * @return The numbers of trips with exact steps.
     */
    public Integer calculateNumberOfTripsWithDistance(String nodeFromId, String nodeToId, Integer distance) throws NodeDoesNotExistInGraphException {
        HashMap<String, Integer> possibleRoutes = new HashMap<String, Integer>();
        Integer result = 0;
        possibleRoutes.put(nodeFromId, 0);
        HashMap<String, Integer> newPossibleRoutes;
        Boolean stillPossibleToFind = true;
        while (stillPossibleToFind) {
            stillPossibleToFind = false;
            newPossibleRoutes = new HashMap<String, Integer>();
            for (Map.Entry<String, Integer> routeDistanceEntry : possibleRoutes.entrySet()) {
                String route = routeDistanceEntry.getKey();
                Integer routeDistance = routeDistanceEntry.getValue();
                Node lastNode = graph.getNode(String.valueOf(route.charAt(route.length() - 1)));
                for (Edge edge : lastNode.getEdges().values()) {
                    if (routeDistance + edge.getCost() < distance) {
                        stillPossibleToFind = true;
                        String newPossibleRoute = route + "-" + edge.getToId();
                        newPossibleRoutes.put(newPossibleRoute, routeDistance + edge.getCost());
                        if (newPossibleRoute.endsWith(nodeToId)) {
                            result++;
                        }
                    }
                }
            }
            possibleRoutes = newPossibleRoutes;
        }
        return result;
    }
}
