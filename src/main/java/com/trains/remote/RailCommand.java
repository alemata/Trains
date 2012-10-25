package com.trains.remote;

import com.trains.GraphLoader;
import com.trains.InconsistentGraphException;
import com.trains.RailRoad;

public enum RailCommand {

    CREATE {
        @Override
        public Object execute(RailRoad railRoad, String[] argsSplit) {
            try {
                railRoad.setGraph(GraphLoader.readGraphFromString(argsSplit[1]));
                return "Graph created";
            } catch (InconsistentGraphException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return "Graph string is not valid";
            }
        }
    }, DISTANCE {
        @Override
        public Object execute(RailRoad railRoad, String[] argsSplit) {
            return railRoad.calculateRouteDistance(argsSplit[1]);

        }
    }, SHORTEST {
        @Override
        public Object execute(RailRoad railRoad, String[] argsSplit) {
            return railRoad.calculateShortestRoute(argsSplit[1], argsSplit[2]);
        }
    }, TRIPSLIMITED {
        @Override
        public Object execute(RailRoad railRoad, String[] argsSplit) {
            return railRoad.calculateNumberOfTripsWithMaxSteps(argsSplit[1], argsSplit[2], Integer.valueOf(argsSplit[3]));
        }
    }, TRIPSEXACT {
        @Override
        public Object execute(RailRoad railRoad, String[] argsSplit) {
            return railRoad.calculateNumberOfTripsWithExactSteps(argsSplit[1], argsSplit[2], Integer.valueOf(argsSplit[3]));
        }
    }, TRIPSDISTANCE {
        @Override
        public Object execute(RailRoad railRoad, String[] argsSplit) {
            return railRoad.calculateNumberOfTripsWithDistance(argsSplit[1], argsSplit[2], Integer.valueOf(argsSplit[3]));
        }
    }, HELP {
        @Override
        public String execute(RailRoad railRoad, String[] argsSplit) {
            return TelnetServer.getHelpString();
        }
    };

    public abstract Object execute(RailRoad railRoad, String[] argsSplit);
}
