package CoreLayer;

import java.net.ServerSocket;
import java.net.Socket;

public class NodoA2 extends Nodo {
    public NodoA2() {
        try {
            color = "\u001B[32m";
            name = "Nodo A2";
            numConexiones = 3;
            server = new ServerSocket(8001);
            sockets = new Socket[numConexiones];
            isEverywhere = true;
            isEager = true;
            isActive = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            sockets[0] = new Socket("localhost", 8000);
            sockets[1] = new Socket("localhost", 9001);
            sockets[2] = server.accept();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + name);
        }
    }

    @Override
    protected void nodoEngine() {
        try {
            super.nodoEngine();

            sockets[1].close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
