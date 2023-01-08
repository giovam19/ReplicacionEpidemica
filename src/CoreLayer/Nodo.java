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

    protected boolean isEverywhere;
    protected boolean isEager;
    protected boolean isActive;

    private static File file = new File("src/transactions.txt");;

    private void startNodo() {
        makeConnections();
        nodoEngine();
    }

    protected void nodoEngine() {
        try {
            /*everywhereEagerActive();

            while (true) {
                for (int i = 0; i < numConexiones; i++) {
                    try {
                        String s = receiveMsgNonBlock(sockets[i]);
                        System.out.println(color + name + " Message Received: " + s);
                    } catch (Exception ignored) {
                    }
                }
            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void everywhereEagerActive() throws IOException {
        for (int i = 0; i < numConexiones; i++) {
            if ((sockets[i].getPort() >= 8000 && sockets[i].getPort() < 9000) || (sockets[i].getLocalPort() >= 8000 && sockets[i].getLocalPort() < 9000)) {
                sendMsg(sockets[i], "Hi Client from " + name);
            }
        }
    }

    private void sendMsg(Socket socket, String msg) throws IOException {
        output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(msg);
    }

    private String receiveMsg(Socket socket) throws Exception {
        input = new ObjectInputStream(socket.getInputStream());
        String message = (String) input.readObject();

        return message;
    }

    private String receiveMsgNonBlock(Socket socket) throws Exception {
        socket.setSoTimeout(5);
        input = new ObjectInputStream(socket.getInputStream());
        String message = (String) input.readObject();

        socket.setSoTimeout(0);
        return message;
    }


    protected void makeConnections() {}

    @Override
    public void run() {
        startNodo();
    }
}
