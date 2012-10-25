package com.trains.remote;

import com.trains.RailRoad;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            System.out.println("Client Connected!");
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader din = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                        out.write("Response => " + railCommand.execute(railRoad, commandSplit) + "\n");
                        out.write(">>");
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
        }
    }

    private boolean closeConnection(OutputStreamWriter out, BufferedReader din) throws IOException {
        boolean connected;
        socket.close();
        System.out.println("Se ha desconectado un cliente.");
        din.close();
        out.close();
        connected = false;
        return connected;
    }
}
