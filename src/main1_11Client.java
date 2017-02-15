import java.io.*;
import java.net.*;
import java.util.Scanner;

public class main1_11Client extends Thread{
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        try
        {
            socket = new Socket("localhost", 5050);
            new Output(socket);
            new Input(socket);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}

class Output extends Thread{
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public Output(Socket s) throws IOException{
        socket = s;
        in = new Scanner(System.in);
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        start();
    }

    public void run(){
        while(true){
            System.out.print("U: ");
            String data = in.nextLine();
            if(data.equals("END")) {
                break;
            }
            out.println(data);
        }
    }
}

class Input extends Thread{
    private Socket socket;
    private BufferedReader in;

    public Input(Socket s) throws IOException{
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    public void run(){
        try {
            while(true){
                String data = in.readLine();
                System.out.println("from " + data);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}