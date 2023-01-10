package CoreLayer;

import java.io.*;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Nodo extends Thread {
    protected String color;
    protected String name;
    protected int numConexiones = 2;

    protected ServerSocketChannel socketChannel;
    protected ServerSocket serverSocket;
    protected Selector selector;
    protected SocketChannel[] client;

    protected Queue<String> queue = new LinkedList<>();
    protected boolean waitAck = false;
    protected int acks = 0;
    protected int[] version = new int[20];
    protected FileWriter fileWriter;
    protected PrintWriter pw;
    protected static int updatesCounter = 0;
    protected boolean flagUpdate = true;
    protected boolean tenSec = false;

    private void startNodo() {
        makeConnections();
        startServer();
    }

    private void startServer() {
        while (true) {
            try {
                if (updatesCounter > 0 && updatesCounter % 10 == 0) {
                    replicateToLayer1();
                } else {
                    flagUpdate = true;
                }

                if (!waitAck && !queue.isEmpty()) {
                    String data = queue.poll();

                    String[] actions = data.split(", ");
                    actions[0] = "BC-"+actions[1];
                    actions[1] = "BC-"+actions[2];
                    actions[2] = "BC-"+actions[3];

                    for (int i = 0; i < 3; i++) {
                        nodoEngine(actions[i]);
                    }

                    continue;
                }

                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid())
                        continue;

                    if (key.isAcceptable()) {
                        //new client
                        SocketChannel client = socketChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        //read on client
                        SocketChannel client = (SocketChannel) key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.clear();
                        client.read(buffer);
                        buffer.flip();

                        //parse from buffer to string
                        String data = new String(buffer.array()).trim();

                        if (data.contains("L12")) {
                            nodoEngine(data);
                        } else if (waitAck && data.contains("WR-ACK")) {
                            //gestionar ack
                            acks++;
                            if (acks == 2) {
                                waitAck = false;
                                acks = 0;
                            }
                        } else if (waitAck && !data.contains("WR-ACK")) {
                            //meter en cola
                            queue.add(data);
                        } else if (!waitAck) {
                            if (data.contains(", c")) {
                                String[] actions = data.split(", ");
                                actions[0] = "BC-" + actions[1];
                                actions[1] = "BC-" + actions[2];
                                actions[2] = "BC-" + actions[3];

                                for (int i = 0; i < 3; i++) {
                                    nodoEngine(actions[i]);
                                }
                            } else {
                                nodoEngine(data);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception server: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    protected void nodoEngine(String data) {}

    protected void everywhereEagerActive(String data) throws Exception {
        if (data.contains("BC-w")) {
            //broadcast message, update and wait acks
            updatesCounter++;

            //make the broadcast
            data = data.replace("BC-", "ACK-");
            for (int i = 0; i < 2; i++) {
                if (client[i] != null)
                    writeToClient(data, client[i]);
            }

            //update
            updateVersion(data);
            System.out.println(color + name + " Update: " + Arrays.toString(version));

            //wait ACK
            waitAck = true;
            acks = 0;
        } else if (data.contains("ACK-w")) {
            //update and send ACk

            //update
            updateVersion(data);
            System.out.println(color + name + " Update: " + Arrays.toString(version));

            //send ACK
            for (int i = 0; i < 2; i++) {
                writeToClient("WR-ACK", client[i]);
            }
        } else if (data.contains("BC-r")) {
            //read only
            manageRead(data);
        }
    }

    private void manageRead(String data) {
        if (data.contains("BC-r")) {
            String s = data.replace("BC-r(", "").replace(")", "");
            int casilla = Integer.parseInt(s);
            System.out.println(color + name + " Read in position " + casilla + ": " + version[casilla-1]);
        } else {
            String[] split = data.replace("L12-", "").replace("r(", "").replace(")", "").split(", ");
            for (int i = 0; i < split.length; i++) {
                int casilla = Integer.parseInt(split[i]);
                System.out.println(color + name + " Read in position " + casilla + ": " + version[casilla-1]);
            }
        }
    }

    private void updateVersion(String data) throws Exception {
        String n = data.replace("ACK-w(", "");
        n = n.replace(")", "");
        String[] split = n.split(",");
        int val = Integer.parseInt(split[0]);
        int pos = Integer.parseInt(split[1]) - 1;
        if (pos > -1 && pos < 20) {
            version[pos] = val;
            String s = Arrays.toString(version) + ", " + new Date() + "\n";
            fileWriter.write(s);
            fileWriter.flush();
        }
    }

    private void replicateToLayer1() throws Exception {
        if ((name.equals("Nodo A2") || name.equals("Nodo A3")) && flagUpdate) {
            String s = "L12-" + Arrays.toString(version);
            writeToClient(s, client[2]);
            flagUpdate = false;
        }
    }

    protected void lazyReplicacionPasiva(String data) throws Exception {
        if (data.contains("r(")) {
            manageRead(data);
        } else {
            String[] split = data.split("-");

            version = stringToArray(split[1]);
            System.out.println(color + name + " Update: " + Arrays.toString(version));

            String s = Arrays.toString(version) + ", " + new Date() + "\n";
            fileWriter.write(s);
            fileWriter.flush();
        }
    }

    private int[] stringToArray(String data) {
        String[] split = data.replaceAll("\\[", "").replaceAll("]", "").split(", ");
        int[] result = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            result[i] = Integer.parseInt(split[i]);
        }

        return result;
    }

    protected void writeToClient(String message, SocketChannel client) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(message.getBytes());
        buffer.flip();
        client.write(buffer);
        Thread.sleep(0, 5);
    }


    protected void makeConnections() {}

    @Override
    public void run() {
        startNodo();
    }
}
