package Handlers;

import DataBase.DBHandler;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_handler {
    private ExecutorService executorService = Executors.newFixedThreadPool(100);
    private ServerSocket serverSocket;

    public  Server_handler()
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
         try {
                while (true) {

                    Socket socket = serverSocket.accept();
                    add(socket);
                }

            } catch (IOException e) {
                e.printStackTrace();
                Controller.observableList.clear();
            }

    }
    public  void add(Socket s)
    {
        //System.out.println("Добавляю");
        executorService.submit(new Client_handler(s,new DBHandler()));
    }
    public  void close()
    {
        executorService.shutdownNow();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
