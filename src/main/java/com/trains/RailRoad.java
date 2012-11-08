package com.trains;

import com.trains.model.Graph;
import com.trains.model.NodeDoesNotExistInGraphException;

public class RailRoad {

    public static final String NO_SUCH_ROUTE = "NO SUCH ROUTE";

    private Graph graph;

    private final DistanceCalculator distanceCalculator = new DistanceCalculator(this);
    private final ShortestRouteCalculator shortestRouteCalculator = new ShortestRouteCalculator(this);
    private final RouteWithStepsCalculation routeWithStepsCalculation = new RouteWithStepsCalculation(this);
    private final RouteWithDistanceCalculator routeWithDistanceCalculator = new RouteWithDistanceCalculator(this);

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
        return distanceCalculator.calculateRouteDistance(route);
    }

    /**
     * Calculates the shortest route between two towns.
     *
     * @param nodeFromId The id of the source town
     * @param nodeToId   The id of the target town
     * @return the shortest distance.
     */
    public String calculateShortestRoute(String nodeFromId, String nodeToId) throws NodeDoesNotExistInGraphException {
        return shortestRouteCalculator.calculateShortestRoute(nodeFromId, nodeToId);
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
        return routeWithStepsCalculation.calculateNumberOfTripsWithSteps(nodeFromId, nodeToId, steps, false);
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
        return routeWithStepsCalculation.calculateNumberOfTripsWithSteps(nodeFromId, nodeToId, steps, true);
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
        return routeWithDistanceCalculator.calculateNumberOfTripsWithDistance(nodeFromId, nodeToId, distance);
    }
}
