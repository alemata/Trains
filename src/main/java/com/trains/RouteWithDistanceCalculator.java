package com.trains;

import com.trains.model.Edge;
import com.trains.model.Node;
import com.trains.model.NodeDoesNotExistInGraphException;

import java.util.HashMap;
import java.util.Map;

public class RouteWithDistanceCalculator {
    private final RailRoad railRoad;

    public RouteWithDistanceCalculator(RailRoad railRoad) {
        this.railRoad = railRoad;
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
        Node nodeFrom = railRoad.getGraph().getNode(nodeFromId);
        Node nodeTo = railRoad.getGraph().getNode(nodeToId);
        HashMap<String, Integer> possibleRoutes = new HashMap<String, Integer>();
        Integer result = 0;
        possibleRoutes.put(nodeFrom.getId(), 0);
        HashMap<String, Integer> newPossibleRoutes;
        Boolean stillPossibleToFind = true;
        while (stillPossibleToFind) {
            stillPossibleToFind = false;
            newPossibleRoutes = new HashMap<String, Integer>();
            for (Map.Entry<String, Integer> routeDistanceEntry : possibleRoutes.entrySet()) {
                String route = routeDistanceEntry.getKey();
                Integer routeDistance = routeDistanceEntry.getValue();
                Node lastNode = railRoad.getGraph().getNode(String.valueOf(route.charAt(route.length() - 1)));
                for (Edge edge : lastNode.getEdges().values()) {
                    if (routeDistance + edge.getCost() < distance) {
                        stillPossibleToFind = true;
                        String newPossibleRoute = route + "-" + edge.getToId();
                        newPossibleRoutes.put(newPossibleRoute, routeDistance + edge.getCost());
                        if (newPossibleRoute.endsWith(nodeTo.getId())) {
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