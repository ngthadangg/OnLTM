package TinhBinhPhuongTCP;

import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server (TCP) đang lắng nghe...");

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Kết nối từ: " + clientSocket.getInetAddress());

                // Xử lý dữ liệu (tính bình phương)
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String data = in.readLine();
                System.out.println("Nhận dữ liệu từ client (TCP): " + data);

                int number = Integer.parseInt(data);
                int result = number * number;

                // Gửi kết quả về client
                out.println(result);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

