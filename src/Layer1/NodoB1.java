package Layer1;

import CoreLayer.Nodo;

import java.net.Socket;

public class NodoB1 extends Nodo {
    public NodoB1() {
        try {
            color = "\u001B[35m";
            name = "Nodo B1";
            numConexiones = 1;
            sockets = new Socket[numConexiones];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            sockets[0] = new Socket("localhost", 8081);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + name);
        }
    }
}
