import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_handler {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private ArrayList<Socket> sockets= new ArrayList<>();
    private ServerSocket serverSocket;

    {
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.out.println("Порт занят");
            e.printStackTrace();
        }
    }
    public  void  listening()
    {
        while (true)
        {
            try {
                Socket socket=serverSocket.accept();
                add(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public  void add(Socket s)
    {
        System.out.println("Добавляю");
        executorService.submit(new Client_handler(s,new DBHandler()));
    }
}
