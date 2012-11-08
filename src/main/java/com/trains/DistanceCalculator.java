package com.trains;

import com.trains.model.Node;
import com.trains.model.NodeDoesNotExistInGraphException;

public class DistanceCalculator {
    private final RailRoad railRoad;

    public DistanceCalculator(RailRoad railRoad) {
        this.railRoad = railRoad;
    }

    /**
     * Calculates the distance for a given route
     *
     *
     * @param route the route to which calculate the distance
     * @return the distance of the route.
     */
    public String calculateRouteDistance(String route) throws NodeDoesNotExistInGraphException {
        Integer result = 0;
        String[] routeSplit = route.split("-");
        Node startingNode = railRoad.getGraph().getNode(routeSplit[0]);

        Node actualNode = startingNode;
        for (int i = 1; i < routeSplit.length; i++) {
            if (actualNode.isNeighbour(railRoad.getGraph().getNode(routeSplit[i]))) {
                result += actualNode.getEdgeTo(routeSplit[i]).getCost();
                actualNode = railRoad.getGraph().getNode(routeSplit[i]);
            } else {
                return RailRoad.NO_SUCH_ROUTE;
            }
        }
        if (result == -1) {
            return RailRoad.NO_SUCH_ROUTE;
        } else {
            return result.toString();
        }
    }
}