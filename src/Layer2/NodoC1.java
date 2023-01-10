package Layer2;

import CoreLayer.Nodo;

import java.net.ServerSocket;
import java.net.Socket;

public class NodoC1 extends Nodo {
    public NodoC1() {
        try {
            color = "\u001B[36m";
            name = "Nodo C1";
            numConexiones = 1;
            server = new ServerSocket(7000);
            sockets = new Socket[numConexiones];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            sockets[0] = server.accept();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + name);
        }
    }
}
