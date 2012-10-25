package com.trains;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TestRailRoad {

    private static final String GRAPH_STRING = "A/B/5/D/5/E/7,B/C/4,C/D/8/E/2,D/C/8/E/6,E/B/3";

    public TestRailRoad() {
    }

    @Test
    public void testDistanceRoute() throws Exception {
        RailRoad railRoad = new RailRoad(GraphLoader.readGraphFromString(GRAPH_STRING));
        assertEquals("Problem with route distance", "9", railRoad.calculateRouteDistance("A-B-C"));
        assertEquals("Problem with route distance", "5", railRoad.calculateRouteDistance("A-D"));
        assertEquals("Problem with route distance", "13", railRoad.calculateRouteDistance("A-D-C"));
        assertEquals("Problem with route distance", "22", railRoad.calculateRouteDistance("A-E-B-C-D"));
        assertEquals("Problem with route distance", "NO SUCH ROUTE", railRoad.calculateRouteDistance("A-E-D"));
    }

    @Test
    public void testShortestPath() throws Exception {
        RailRoad railRoad = new RailRoad(GraphLoader.readGraphFromString(GRAPH_STRING));
        assertEquals("Problem with shortest path", new Integer(9), railRoad.calculateShortestRoute("A", "C"));
        assertEquals("Problem with shortest path", new Integer(9), railRoad.calculateShortestRoute("B", "B"));
        assertEquals("Problem with shortest path", new Integer(9), railRoad.calculateShortestRoute("E", "E"));
        assertEquals("Problem with shortest path", new Integer(16), railRoad.calculateShortestRoute("D", "D"));
        assertEquals("Problem with shortest path", new Integer(-1), railRoad.calculateShortestRoute("A", "A"));
    }

    @Test
    public void testNumberOfTripsExactSteps() throws Exception {
        RailRoad railRoad = new RailRoad(GraphLoader.readGraphFromString(GRAPH_STRING));
        assertEquals("Problem with number of trips", new Integer(3), railRoad.calculateNumberOfTripsWithExactSteps("A", "C", new Integer(4)));
        assertEquals("Problem with number of trips", new Integer(3), railRoad.calculateNumberOfTripsWithExactSteps("A", "E", new Integer(4)));
    }

    @Test
    public void testNumberOfTripsMaxSteps() throws Exception {
        RailRoad railRoad = new RailRoad(GraphLoader.readGraphFromString(GRAPH_STRING));
        assertEquals("Problem with number of trips", new Integer(2), railRoad.calculateNumberOfTripsWithMaxSteps("C", "C", new Integer(3)));
    }

    @Test
    public void testNumberOfTripsWithDistance() throws Exception {
        RailRoad railRoad = new RailRoad(GraphLoader.readGraphFromString(GRAPH_STRING));
        assertEquals("Problem with number of trips distance", new Integer(7), railRoad.calculateNumberOfTripsWithDistance("C", "C", new Integer(30)));
    }
}
