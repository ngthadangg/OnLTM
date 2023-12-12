package OnCuoiKy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        // Tạo socket
        Socket socket = new Socket("localhost", 8080);
        System.out.println("Ket noi den server thanh cong");

        // Gửi dữ liệu cho máy chủ
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("10".getBytes());
        System.out.println("Du lieu gui di may chu: " + 10);

        // Nhận dữ liệu từ máy chủ
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int length = inputStream.read(bytes);
        String message = new String(bytes, 0, length);

        // Xử lý dữ liệu
        System.out.println("Ket qua nhan duoc tu may chu: " + message);

        // Đóng socket
        socket.close();
    }

}
