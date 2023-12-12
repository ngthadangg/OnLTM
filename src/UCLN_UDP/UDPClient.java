package UCLN_UDP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Khởi tạo socket UDP
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            int serverPort = 9876;

            Scanner scanner = new Scanner(System.in);

            // Nhập hai số từ người dùng
            System.out.print("Nhập số thứ nhất: ");
            int num1 = scanner.nextInt();
            System.out.print("Nhập số thứ hai: ");
            int num2 = scanner.nextInt();

            // Gửi dữ liệu đến server
            String data = num1 + "," + num2;
            byte[] sendData = data.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // Nhận kết quả từ server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("UCLN của " + num1 + " và " + num2 + " là: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

