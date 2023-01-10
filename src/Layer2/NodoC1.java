package Layer2;

import CoreLayer.Nodo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Arrays;

public class NodoC1 extends Nodo {
    public NodoC1() {
        try {
            color = "\u001B[36m";
            name = "Nodo C1";
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            serverSocket = socketChannel.socket();

            serverSocket.bind(new InetSocketAddress("localhost", 7000));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, socketChannel.validOps(), null);

            Arrays.fill(version, 0);
            fileWriter = new FileWriter("src/Layer2/logVersionesC1.txt", false);
            pw = new PrintWriter(fileWriter, false);
            pw.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void makeConnections() {
        try {
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + name);
        }
    }

    @Override
    protected void nodoEngine(String data) {
        try {
            lazyReplicacionPasiva(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
