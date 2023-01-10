import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.Scanner;

public class TransactionReader {
    private ServerSocket server;
    private Socket[] coreLayer;
    private Socket layer1;
    private Socket layer2;
    private File file;
    private ObjectOutputStream output;

    private SocketChannel[] cCoreLayer;

    public TransactionReader() {
        try {
            file = new File("src/transactions.txt");
            /*server = new ServerSocket(5000);

            coreLayer = new Socket[3];
            coreLayer[0] = server.accept();
            coreLayer[1] = server.accept();
            coreLayer[2] = server.accept();*/
            cCoreLayer = new SocketChannel[3];
            cCoreLayer[0] = SocketChannel.open(new InetSocketAddress("localhost", 8000));
            cCoreLayer[1] = SocketChannel.open(new InetSocketAddress("localhost", 8001));
            cCoreLayer[2] = SocketChannel.open(new InetSocketAddress("localhost", 8002));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void readTransactions() {
        try {
            Scanner scanner = new Scanner(file);
            Random rand = new Random();

            while (scanner.hasNextLine()) {
                int n = rand.nextInt(1000-500+1) + 500;
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
        } else {
            //write
            int j = rand.nextInt(3);
            writeToServer(data, cCoreLayer[j]);
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
