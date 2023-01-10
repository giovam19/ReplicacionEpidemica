package CoreLayer;

import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NodoA1 extends Nodo {
    public NodoA1() {
        try {
            color = "\u001B[31m";
            name = "Nodo A1";
            /*server = new ServerSocket(8000);
            sockets = new Socket[numConexiones];*/
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            serverSocket = socketChannel.socket();

            serverSocket.bind(new InetSocketAddress("localhost", 8000));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, socketChannel.validOps(), null);

            client = new SocketChannel[numConexiones];
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            client[0] = SocketChannel.open(new InetSocketAddress("localhost", 8001));
            client[1] = SocketChannel.open(new InetSocketAddress("localhost", 8002));
            /*socketReader = new Socket("localhost", 5000);
            for (int i = 0; i < numConexiones; i++) {
                sockets[i] = server.accept();
            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + name);
        }
    }

    @Override
    protected void nodoEngine(String data) {
        try {
            everywhereEagerActive(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
