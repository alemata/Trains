package com.trains.remote;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class TelnetServer {

    private static Logger logger = Logger.getLogger(TelnetServer.class.getName());

    public static final int CONNECTION_PORT = 8088;

    public static void main(String args[]) throws IOException {
        try {
            ServerSocket srvr = new ServerSocket(CONNECTION_PORT);
            while (true) {
                Socket skt = srvr.accept();
                Thread serverThread = new Thread(new ClientHandler(skt));
                serverThread.start();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error", e);
            e.printStackTrace();
        }
    }

    public static String getHelpString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("--------------------------------------------------------------------- \n");
        stringBuffer.append("Usage:");
        stringBuffer.append("You can run commands with this pattern: command AR1 ARG2...   \n");
        stringBuffer.append("Possible commands are: \n\n");
        stringBuffer.append("create: Creates a new RailRoad. Args: railRoadString \n");
        stringBuffer.append("Example = 'create A/B/5/D/5/E/7,B/C/4,C/D/8/E/2,D/C/8/E/6,E/B/3' \n\n");
        stringBuffer.append("distance: Calculate a distance for a given path. Args: routePath \n");
        stringBuffer.append("Example = 'distance A-B-C' \n\n");
        stringBuffer.append("shortest: Calculate the shortest route between two towns. Args: town1 town2 \n");
        stringBuffer.append("Example = 'shortest A C' \n \n");
        stringBuffer.append("tripslimited: Calculate the number of trips between two towns with a maximum of stops. Args: town1 town2 stopsLimit \n");
        stringBuffer.append("Example = 'tripslimited A C 3' \n \n");
        stringBuffer.append("tripsexact: Calculate the number of trips between two towns with exactly a number of stops. Args: town1 town2 stopsAmount \n");
        stringBuffer.append("Example = 'tripsexact A C 4' \n \n");
        stringBuffer.append("tripsdistance: Calculate the number of trips between two towns with a distance less than a maximum. Args: town1 town2 maxDistance \n");
        stringBuffer.append("Example = 'tripsdistance A C 30' \n\n");
        stringBuffer.append("---------------------------------------------------------------------");
        return stringBuffer.toString();
    }
}
