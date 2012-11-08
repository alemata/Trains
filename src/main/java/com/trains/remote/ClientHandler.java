package com.trains.remote;

import com.trains.RailRoad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        OutputStreamWriter out = null;
        BufferedReader din = null;
        try {
            System.out.println("Client Connected!");
            out = new OutputStreamWriter(socket.getOutputStream());
            din = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String commandFromClient = "";
            RailRoad railRoad = new RailRoad();
            boolean connected = true;
            while (connected) {
                try {
                    commandFromClient = din.readLine();
                    String[] commandSplit = commandFromClient.split(" ");
                    String commandName = commandSplit[0];
                    if ("quit".equalsIgnoreCase(commandName) || "^]".equalsIgnoreCase(commandName)) {
                        break;
                    } else {
                        RailCommand railCommand = RailCommand.valueOf(commandName.toUpperCase());
                        System.out.println("Command: " + commandFromClient);
                        if (railRoad.getGraph() != null || railCommand.equals(RailCommand.CREATE) || railCommand.equals(RailCommand.HELP)) {
                            out.write("Response => " + railCommand.execute(railRoad, commandSplit) + "\n");
                            out.write(">>");
                        } else {
                            out.write("Error: You must create a graph first\n");
                            out.write(">>");
                        }
                        out.flush();
                    }
                } catch (IllegalArgumentException e) {
                    out.write("'" + commandFromClient + "' is not a valid command \n" + "Try 'help' for more information" + "\n");
                    out.write(">>");
                    out.flush();
                }
            }
            closeConnection(out, din);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection(out, din);
        }
    }

    private boolean closeConnection(OutputStreamWriter out, BufferedReader din) {
        boolean connected;
        try {
            out.close();
            socket.close();
            System.out.println("Se ha desconectado un cliente.");
            din.close();
            connected = false;
            return connected;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
