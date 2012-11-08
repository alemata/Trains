package com.trains;

import com.trains.model.Edge;
import com.trains.model.Node;
import com.trains.model.NodeDoesNotExistInGraphException;

import java.util.ArrayList;

public class RouteWithStepsCalculation {
    private final RailRoad railRoad;

    public RouteWithStepsCalculation(RailRoad railRoad) {
        this.railRoad = railRoad;
    }

    Integer calculateNumberOfTripsWithSteps(String nodeFromId, String nodeToId, Integer steps, Boolean mustBeExact) throws NodeDoesNotExistInGraphException {
        Node nodeFrom = railRoad.getGraph().getNode(nodeFromId);
        Node nodeTo = railRoad.getGraph().getNode(nodeToId);
        Integer result = 0;
        ArrayList<String> possibleRoutes = new ArrayList<String>();
        ArrayList<String> newPossibleRoutes;
        possibleRoutes.add(nodeFrom.getId());
        while (steps-- > 0) {
            newPossibleRoutes = new ArrayList<String>();
            for (String possibleRoute : possibleRoutes) {
                Node lastNode = railRoad.getGraph().getNode(String.valueOf(possibleRoute.charAt(possibleRoute.length() - 1)));
                for (Edge edge : lastNode.getEdges().values()) {
                    String newPossibleRoute = possibleRoute + "-" + edge.getToId();
                    newPossibleRoutes.add(newPossibleRoute);
                    if (!mustBeExact && newPossibleRoute.endsWith(nodeTo.getId())) {
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
}