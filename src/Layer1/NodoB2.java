package Layer1;

import CoreLayer.Nodo;

import java.net.ServerSocket;
import java.net.Socket;

public class NodoB2 extends Nodo {
    public NodoB2() {
        try {
            color = "\u001B[34m";
            name = "Nodo B2";
            numConexiones = 3;
            server = new ServerSocket(9000);
            sockets = new Socket[numConexiones];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            sockets[0] = server.accept();
            sockets[1] = new Socket("localhost", 7000);
            sockets[2] = new Socket("localhost", 7001);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
