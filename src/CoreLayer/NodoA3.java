package CoreLayer;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NodoA3 extends Nodo {
    public NodoA3() {
        try {
            color = "\u001B[33m";
            name = "Nodo A3";
            numConexiones = 2;
            /*server = new ServerSocket(8002);
            sockets = new Socket[numConexiones];*/
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            serverSocket = socketChannel.socket();

            serverSocket.bind(new InetSocketAddress("localhost", 8002));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, socketChannel.validOps(), null);

            client = new SocketChannel[numConexiones];
            Arrays.fill(version, 0);
            fileWriter = new FileWriter("src/CoreLayer/logVersionesA3.txt", false);
            pw = new PrintWriter(fileWriter, false);
            pw.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
            /*socketReader = new Socket("localhost", 5000);
            sockets[0] = new Socket("localhost", 8000);
            sockets[1] = new Socket("localhost", 8001);
            sockets[2] = new Socket("localhost", 9000);*/
            client[0] = SocketChannel.open(new InetSocketAddress("localhost", 8000));
            client[1] = SocketChannel.open(new InetSocketAddress("localhost", 8001));
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
