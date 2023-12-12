package XuLyDate;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
public class UDPServer {
    public static void main(String[] args) {
        int portNumber = 12345;

        try (DatagramSocket socket = new DatagramSocket(portNumber)) {
            System.out.println("Server is listening on port " + portNumber);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String clientInput = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received from client: " + clientInput);

                if (isValidDateFormat(clientInput)) {
                    String response = getDateParts(clientInput);
                    byte[] sendData = response.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(sendPacket);
                } else {
                    String response = "Invalid date format: " + clientInput;
                    byte[] sendData = response.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    socket.send(sendPacket);
                }
            }
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
    private static boolean isValidDateFormat(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String getDateParts(String input) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        try {
            Date date = inputFormat.parse(input);
            String day = dayFormat.format(date);
            String month = monthFormat.format(date);
            String year = yearFormat.format(date);

            if (checkPrime(Integer.parseInt(day))){
                return "Day: " + day + " is Prime" ;
            }

            return "Day: " + day + ", Month: " + month + ", Year: " + year;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing date";
        }
    }
    private static boolean checkPrime(int number){
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
