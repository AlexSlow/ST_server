package Handlers;

import DataBase.DBHandler;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static res.RESURSE.INI_FILE;

public class Server_handler {
    private ExecutorService executorService = Executors.newFixedThreadPool(100);
    private ServerSocket serverSocket;

    public  Server_handler()
    {
        try {
            FileReader fr= new FileReader(INI_FILE);
            Properties properties=new Properties();
            properties.load(fr);
            serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("port","5000")));
            fr.close();
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
