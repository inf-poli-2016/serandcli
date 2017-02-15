import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class main1_11Server extends Thread{
    public static ArrayList connections = new ArrayList();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5050, 0, InetAddress.getByName("localhost"));

        System.out.println("Server start.");

        while (true)
        {
            Socket clientSocket = server.accept();
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            talker tal = new talker(clientSocket);
            connections.add(pw);
        }
    }
}

class talker extends Thread
{
    private Socket client;
    private BufferedReader input;
    private PrintStream output;

    public talker(Socket client) throws IOException
    {
        this.client = client;
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintStream(client.getOutputStream());
        start();
    }

    public void run(){

        try {
            while (true){
                String data = input.readLine();
                if (data != null)
                {
                    System.out.println("Server: " + data);

                    Iterator iterator = main1_11Server.connections.iterator();
                    while (iterator.hasNext())
                    {
                        PrintWriter pw = (PrintWriter) iterator.next();
                        pw.println(data);
                        pw.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}