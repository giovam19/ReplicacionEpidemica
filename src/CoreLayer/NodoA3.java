package CoreLayer;

import java.net.Socket;

public class NodoA3 extends Nodo {
    public NodoA3() {
        try {
            color = "\u001B[33m";
            name = "Nodo A3";
            numConexiones = 3;
            sockets = new Socket[numConexiones];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            sockets[0] = new Socket("localhost", 8080);
            sockets[1] = new Socket("localhost", 8081);
            sockets[2] = new Socket("localhost", 8090);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + name);
        }
    }
}
