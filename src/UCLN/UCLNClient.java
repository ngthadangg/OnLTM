package UCLN;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UCLNClient {
    private Socket socket = null;
    private BufferedReader input = null;
    private DataOutputStream output = null;

    public UCLNClient(String address, int post) {
        try {
            socket = new Socket(address, post);
            System.out.println("Connetced to server!");

            input = new BufferedReader(new InputStreamReader(System.in));
            output = new DataOutputStream(socket.getOutputStream());

            System.out.println("Nhap 2 so gui den server: ");

            String line = "";
            line = input.readLine();
            output.writeUTF(line);

            line = input.readLine();
            output.writeUTF(line);

            System.out.println("Server dang xu ly. . .");

            DataInputStream resultInput = new DataInputStream(socket.getInputStream());
            System.out.println(resultInput.readUTF());

            System.out.println("Close connnect!");

            socket.close();
            input.close();

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) throws UnknownHostException {
        // TODO Auto-generated method stub
        InetAddress myHost = InetAddress.getLocalHost();
        String address = myHost.getHostAddress();
        int port = 5555;

        UCLNClient UCLN = new UCLNClient(address, port);
    }
}
