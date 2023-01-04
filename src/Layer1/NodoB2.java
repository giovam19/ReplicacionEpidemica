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
            server = new ServerSocket(8090);
            sockets = new Socket[numConexiones];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            for (int i = 0; i < numConexiones; i++) {
                sockets[i] = server.accept();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void nodoEngine() {
        try {
            super.nodoEngine();

            sockets[0].close();
            server.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
