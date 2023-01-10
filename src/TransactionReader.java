import java.io.File;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.Scanner;

public class TransactionReader {
    private File file;
    private int minTime = 500;
    private int maxTime = 1000;

    private SocketChannel[] nodos;

    public TransactionReader() {
        try {
            file = new File("src/transactions.txt");

            nodos = new SocketChannel[5];
            nodos[0] = SocketChannel.open(new InetSocketAddress("localhost", 8000));
            nodos[1] = SocketChannel.open(new InetSocketAddress("localhost", 8001));
            nodos[2] = SocketChannel.open(new InetSocketAddress("localhost", 8002));
            nodos[3] = SocketChannel.open(new InetSocketAddress("localhost", 9001));
            nodos[4] = SocketChannel.open(new InetSocketAddress("localhost", 7001));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readTransactions() {
        try {
            Scanner scanner = new Scanner(file);
            Random rand = new Random();

            while (scanner.hasNextLine()) {
                int n = rand.nextInt(maxTime-minTime+1) + minTime;
                Thread.sleep(n);
                String data = scanner.nextLine();

                manageTransaction(data);
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void manageTransaction(String data) {
        Random rand = new Random();
        if (data.contains("b<")) {
            //read
            String[] split = data.replace("b<", "").replace(">", "").split(", ");
            int nodo = Integer.parseInt(split[0]);
            String s = "L12-"+split[1]+", "+split[2]+", "+split[3];

            switch (nodo) {
                case 0:
                    writeToServer(data, nodos[0]);
                    break;
                case 1:
                    writeToServer(s, nodos[3]);
                    break;
                case 2:
                    writeToServer(s, nodos[4]);
                    break;
            }
        } else {
            //write
            int j = rand.nextInt(3);
            writeToServer(data, nodos[j]);
        }
    }

    private void writeToServer(String message, SocketChannel client)   {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(message.getBytes());
            buffer.flip();
            client.write(buffer);
            Thread.sleep(0, 50);
        } catch (Exception e) {
            System.out.println("write server - " + e.getMessage());
        }
    }
}
