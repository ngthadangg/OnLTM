package XuLyDate;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
public class UDPClient {


    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;


        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a date (dd/MM/yyyy):");
            String userInput = scanner.nextLine();

            byte[] sendData = userInput.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverInetAddress, serverPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server response: " + serverResponse);

            closeConnection(socket);;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void closeConnection(DatagramSocket socket) {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("Connection closed");
        }
    }
}
