package OnCuoiKy;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        int port = 8080;
        // Tạo socket
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server dang lang nghe tren cong: " + port);

        // Chờ kết nối từ máy khách
        Socket socket = serverSocket.accept();
        System.out.println("Nhan ket noi tu client");


        // Nhận dữ liệu từ máy khách
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int length = inputStream.read(bytes);
        int number = Integer.parseInt(new String(bytes, 0, length));

        System.out.println("Du lieu nhan tu client: "+ number);

        // Xử lý dữ liệu
        int result = number * 2;

        // Gửi dữ liệu cho máy khách
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(String.valueOf(result).getBytes());

        // Đóng socket
        socket.close();
        serverSocket.close();
    }
}
