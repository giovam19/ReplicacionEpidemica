import CoreLayer.NodoA1;
import CoreLayer.NodoA2;
import CoreLayer.NodoA3;
import Layer1.NodoB1;
import Layer1.NodoB2;
import Layer2.NodoC1;
import Layer2.NodoC2;

public class Main {
        public static void main(String[] args) {
        try {
            NodoA1 a1 = new NodoA1();
            NodoA2 a2 = new NodoA2();
            NodoA3 a3 = new NodoA3();
            /*NodoB1 b1 = new NodoB1();
            NodoB2 b2 = new NodoB2();
            NodoC1 c1 = new NodoC1();
            NodoC2 c2 = new NodoC2();*/

            a1.start();
            a2.start();
            a3.start();
            /*b1.start();
            b2.start();
            c1.start();
            c2.start();*/

            TransactionReader reader = new TransactionReader();
            Thread.sleep(1000);
            reader.readTransactions();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
