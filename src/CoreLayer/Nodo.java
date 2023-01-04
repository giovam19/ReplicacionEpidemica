package CoreLayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Nodo extends Thread {
    protected String color;

    protected String name;
    protected int numConexiones = 2;
    protected ServerSocket server;
    protected Socket[] sockets;
    protected ObjectInputStream input;
    protected ObjectOutputStream output;

    private void startNodo() {
        makeConnections();
        nodoEngine();
    }

    protected void nodoEngine() {
        try {
            for (int i = 0; i < numConexiones; i++) {
                output = new ObjectOutputStream(sockets[i].getOutputStream());
                output.writeObject("Hi Client from " + name);
            }

            for (int i = 0; i < numConexiones; i++) {
                input = new ObjectInputStream(sockets[i].getInputStream());
                String message = (String) input.readObject();
                System.out.println(color + " " + name + " Message Received: " + message);
            }

            input.close();
            output.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void makeConnections() {}

    @Override
    public void run() {
        startNodo();
    }
}
