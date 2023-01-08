import CoreLayer.NodoA1;
import CoreLayer.NodoA2;
import CoreLayer.NodoA3;
import Layer1.NodoB1;
import Layer1.NodoB2;
import Layer2.NodoC1;
import Layer2.NodoC2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        NodoA1 a1 = new NodoA1();
        NodoA2 a2 = new NodoA2();
        NodoA3 a3 = new NodoA3();
        NodoB1 b1 = new NodoB1();
        NodoB2 b2 = new NodoB2();
        NodoC1 c1 = new NodoC1();
        NodoC2 c2 = new NodoC2();

        a1.start();
        a2.start();
        a3.start();
        b1.start();
        b2.start();
        c1.start();
        c2.start();

        //readTransactions();
    }

    private static void readTransactions() {
        try {
            File file = new File("src/transactions.txt");
            Scanner scanner = new Scanner(file);
            Random rand = new Random();

            while (scanner.hasNextLine()) {
                int n = rand.nextInt(1000-500+1) + 500;
                Thread.sleep(n);
                String data = scanner.nextLine();
                System.out.println(data);
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
