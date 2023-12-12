package UCLN;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UCLNServer {
    private Socket socket = null;
    private ServerSocket  server = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;


    public UCLNServer(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server is start! ");
            System.out.println("Wating for client . . . ");

            socket = server.accept();
            System.out.println("Client is connnected!");

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));


            long a = Long.parseLong(input.readUTF());
            long b = Long.parseLong(input.readUTF());
            System.out.println("Hai so nhan duoc tu client la: a = " + a + ", b = " + b);

            long c = UCLN(a, b);
            output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF("UCLN cua "+ a + " va " + b + " la: " + c);

            System.out.println("Close connnect!");

            socket.close();
            input.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
    public static long UCLN(long a, long b) {
        return b == 0 ? a : UCLN(b,  a% b );
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UCLNServer UCLN = new UCLNServer(5555);
    }

}
