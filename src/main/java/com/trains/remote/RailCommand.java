package com.trains.remote;

import com.trains.GraphLoader;
import com.trains.InconsistentGraphException;
import com.trains.NodeDoesNotExistInGraphException;
import com.trains.RailRoad;

public enum RailCommand {

    CREATE {
        @Override
        public Object doExecute(RailRoad railRoad, String[] argsSplit) throws InconsistentGraphException {
            railRoad.setGraph(GraphLoader.readGraphFromString(argsSplit[1]));
            return "Graph created";
        }
    }, DISTANCE {
        @Override
        public Object doExecute(RailRoad railRoad, String[] argsSplit) throws NodeDoesNotExistInGraphException {
            return railRoad.calculateRouteDistance(argsSplit[1]);
        }
    }, SHORTEST {
        @Override
        public Object doExecute(RailRoad railRoad, String[] argsSplit) throws NodeDoesNotExistInGraphException {
            return railRoad.calculateShortestRoute(argsSplit[1], argsSplit[2]);
        }
    }, TRIPSLIMITED {
        @Override
        public Object doExecute(RailRoad railRoad, String[] argsSplit) throws NodeDoesNotExistInGraphException {
            return railRoad.calculateNumberOfTripsWithMaxSteps(argsSplit[1], argsSplit[2], Integer.valueOf(argsSplit[3]));
        }
    }, TRIPSEXACT {
        @Override
        public Object doExecute(RailRoad railRoad, String[] argsSplit) throws NodeDoesNotExistInGraphException {
            return railRoad.calculateNumberOfTripsWithExactSteps(argsSplit[1], argsSplit[2], Integer.valueOf(argsSplit[3]));
        }
    }, TRIPSDISTANCE {
        @Override
        public Object doExecute(RailRoad railRoad, String[] argsSplit) throws NodeDoesNotExistInGraphException {
            return railRoad.calculateNumberOfTripsWithDistance(argsSplit[1], argsSplit[2], Integer.valueOf(argsSplit[3]));
        }
    }, HELP {
        @Override
        public String doExecute(RailRoad railRoad, String[] argsSplit) {
            return TelnetServer.getHelpString();
        }
    };

    public abstract Object doExecute(RailRoad railRoad, String[] argsSplit) throws NodeDoesNotExistInGraphException, InconsistentGraphException;


    public Object execute(RailRoad railRoad, String[] argsSplit) {
        try {
            return doExecute(railRoad, argsSplit);
        } catch (NodeDoesNotExistInGraphException e) {
            return "Node '" + e.getNodeId() + "' does not exist in graph";
        } catch (InconsistentGraphException e) {
            return "Graph string is not valid";
        }
    }
}
