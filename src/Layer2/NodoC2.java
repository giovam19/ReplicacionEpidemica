package Layer2;

import CoreLayer.Nodo;

import java.net.ServerSocket;
import java.net.Socket;

public class NodoC2 extends Nodo {
    public NodoC2() {
        try {
            color = "\u001B[37m";
            name = "Nodo C2";
            numConexiones = 1;
            server = new ServerSocket(7001);
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
