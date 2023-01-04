package CoreLayer;

import java.net.ServerSocket;
import java.net.Socket;

public class NodoA1 extends Nodo {
    public NodoA1() {
        try {
            color = "\u001B[31m";
            name = "Nodo A1";
            server = new ServerSocket(8080);
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
            System.out.println(e.getMessage() + " " + name);
        }
    }

    @Override
    protected void nodoEngine() {
        try {
            super.nodoEngine();

            for (int i = 0; i < numConexiones; i++) {
                sockets[i].close();
            }
            server.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
