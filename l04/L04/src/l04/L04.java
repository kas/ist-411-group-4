package l04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class L04 {
    public L04() {
        System.out.println("Web Server Started");

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                System.out.println("Waiting for client request");

                Socket remote = serverSocket.accept();

                System.out.println("Connection made");

                new Thread(new ClientHandler(remote)).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new L04();
    }
}
