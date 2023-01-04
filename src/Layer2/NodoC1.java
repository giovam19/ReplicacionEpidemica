package Layer2;

import CoreLayer.Nodo;

import java.net.Socket;

public class NodoC1 extends Nodo {
    public NodoC1() {
        try {
            color = "\u001B[36m";
            name = "Nodo C1";
            numConexiones = 1;
            sockets = new Socket[numConexiones];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            sockets[0] = new Socket("localhost", 8090);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + name);
        }
    }
}
