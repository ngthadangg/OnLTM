package Messager;

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMess {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public ClientMess(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to Server");

            input = new DataInputStream(System.in);
            output = new DataOutputStream(socket.getOutputStream());

            // Luồng gửi tin nhắn đến server
            Thread sendThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String line = "";
                    while (!line.equals("End")) {
                        try {
                            line = input.readLine();
                            output.writeUTF(line);
                        } catch (IOException i) {
                            System.out.println(i);
                        }
                    }
                }

            });
            sendThread.start();

            // Luồng nhận tin nhắn từ server
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try (DataInputStream serverInput = new DataInputStream(socket.getInputStream())) {
                        String response = "";
                        while (!response.equals("End")) {
                            if (socket.isClosed()) System.exit(0);
                            else {
                                response = serverInput.readUTF();
                                System.out.println(response);
                            }

                        }
                    } catch (IOException i) {
                        System.exit(0);
                        // xử lý ngoại lệ khi server bị đóng đột ngột
                        System.out.println("Connection closed by server.");
                    }
                }

            });
            receiveThread.start();

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
    private void closeConnection() {
        System.out.println("Closing connection");
        // đóng kết nối
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
    public static void main(String[] args) {
        ClientMess client = new ClientMess("127.0.0.1", 5555);
    }
}
