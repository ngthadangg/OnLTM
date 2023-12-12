package TinhBinhPhuongTCP;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket("127.0.0.1", 12345);

            // Gửi dữ liệu đến server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Nhập một số nguyên: ");
            String data = userInput.readLine();
            out.println(data);

            // Nhận kết quả từ server
            String result = in.readLine();
            System.out.println("Kết quả từ server (TCP): " + result);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
