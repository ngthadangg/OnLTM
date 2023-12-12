package Messager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
public class ServerMess {

    // Khởi tạo  socket và input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    // Khởi tạo hàm dựng với cổng
    public ServerMess(int port)
    {

        // Bắt đầu server và đợi client kết nối
        try
        {
            server = new ServerSocket(port);

            System.out.println("Server Two Way started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();

            System.out.println("Client accepted");

            // Nhận tin nhắn từ client
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());

            // Luồng nhận tin nhắn từ client
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String line = "";
                    // xuất tin nhắn của client cho đến khi nhận được chữ "End"
                    while (!line.equals("End")) {
                        if (!socket.isClosed()) {
                            try {
                                if (socket.isClosed()) {
                                    System.exit(0);
                                    System.out.println("Kết nối đã bị ngắt. Vui lòng kiểm tra lại.");

                                }
                                line = input.readUTF();
                                System.out.println(line);
                            } catch (SocketException e) {
                                System.exit(0);
                                System.out.println("Kết nối đã bị ngắt. Vui lòng kiểm tra lại.");
                                // Thoát khỏi chương trình hoặc thử kết nối lại
                            } catch (IOException i) {
                                System.out.println(i);
                            }
                        }

                    }
                    closeConnection();
                }
            });
            receiveThread.start();

            // Luồng gửi tin nhắn cho client
            Thread sendThread  = new Thread(new Runnable() {
                @Override
                public void run() {
                    String response = "";
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                    // Gửi tin nhắn liên tục cho đến khi gửi chữ "End"
                    while (!response.equals("End")) {
                        try {
                            if (socket.isClosed()) {
                                System.out.println("Kết nối đã bị ngắt. Vui lòng kiểm tra lại.");
                                System.exit(0);
                            }
                            response = reader.readLine();
                            output.writeUTF(response);
                        }  catch (SocketException e) {
                            System.exit(0);
                            System.out.println("Kết nối đã bị ngắt ngắt ngắt. Vui lòng kiểm tra lại.");

                            // Thoát khỏi chương trình hoặc thử kết nối lại
                        } catch (IOException i) {
                            System.out.println(i);
                        }
                    }
                    closeConnection();
                }
            });
            sendThread.start();
        }

        catch (IOException i) {
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
    public static void main(String[] args)
    {
        ServerMess server = new ServerMess(5555);
    }
}
