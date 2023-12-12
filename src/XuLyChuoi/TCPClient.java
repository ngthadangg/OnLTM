package XuLyChuoi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        try (Socket socket = new Socket(serverAddress, serverPort);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)) {

            System.out.println("Enter a string:");
            String userInput = scanner.nextLine();

            // Gửi chuỗi đến server
            out.println(userInput);

            // Nhận và in ra phản hồi từ server
            String serverResponse = in.readLine();
            System.out.println("Server response: " + serverResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
