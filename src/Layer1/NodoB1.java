package Layer1;

import CoreLayer.Nodo;

import java.net.ServerSocket;
import java.net.Socket;

public class NodoB1 extends Nodo {
    public NodoB1() {
        try {
            color = "\u001B[35m";
            name = "Nodo B1";
            numConexiones = 1;
            server = new ServerSocket(9001);
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
