package TinhBinhPhuongUDP;

import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(12346);
            System.out.println("Server (UDP) đang lắng nghe...");

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                // Xử lý dữ liệu (tính bình phương)
                String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Nhận dữ liệu từ client (UDP): " + data);

                int number = Integer.parseInt(data);
                int result = number * number;

                // Gửi kết quả về client
                byte[] sendData = String.valueOf(result).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }
}
