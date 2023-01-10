package Layer1;

import CoreLayer.Nodo;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NodoB2 extends Nodo {
    private Thread count10sec;
    public NodoB2() {
        try {
            color = "\u001B[34m";
            name = "Nodo B2";
            numConexiones = 2;
            selector = Selector.open();
            socketChannel = ServerSocketChannel.open();
            serverSocket = socketChannel.socket();

            serverSocket.bind(new InetSocketAddress("localhost", 9001));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, socketChannel.validOps(), null);

            tenSec = false;
            client = new SocketChannel[numConexiones];
            Arrays.fill(version, 0);
            fileWriter = new FileWriter("src/Layer1/logVersionesB2.txt", false);
            pw = new PrintWriter(fileWriter, false);
            pw.flush();

            initThread();
            count10sec.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initThread() {
        count10sec = new Thread(){
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                        tenSec = true;
                        String s = "L12-" + Arrays.toString(version);
                        for (int i = 0; i < 2; i++) {
                            writeToClient(s, client[i]);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    protected void makeConnections() {
        try {
            client[0] = SocketChannel.open(new InetSocketAddress("localhost", 7000));
            client[1] = SocketChannel.open(new InetSocketAddress("localhost", 7001));
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
