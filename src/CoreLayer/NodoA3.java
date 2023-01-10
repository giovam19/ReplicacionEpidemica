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
            numConexiones = 3;
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
            client[0] = SocketChannel.open(new InetSocketAddress("localhost", 8000));
            client[1] = SocketChannel.open(new InetSocketAddress("localhost", 8001));
            client[2] = SocketChannel.open(new InetSocketAddress("localhost", 9001));
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
