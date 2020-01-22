package Handlers;

import DataBase.DBHandler;
import DataBase.FXMLparser;
import Sql_commands.SQLcommand;
import javafx.application.Platform;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client_handler  implements Runnable {
    private Socket socket;
    private DBHandler dbHandler;
     private PrintWriter printWriter;

    public Client_handler(Socket s,DBHandler dbHandler) {
        socket = s;
        this.dbHandler=dbHandler;
        Platform.runLater(new Runnable(){
            @Override
            public  void  run()
            {
                Controller.observableList.add(socket.toString());
            }
        ///
        });


    }

    @Override
    public void run() {

        try( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String xml,str,str1;
            printWriter=new PrintWriter(socket.getOutputStream());
                while ((str = bufferedReader.readLine()) != null) {

                    if (str.equals("start")) {
                       // str = "";
                        xml = "";
                        while (!((str1 = bufferedReader.readLine()).equals("end"))) {
                            xml = xml + str1 + "\n";
                        }


                        //System.out.println("от клиента " + xml);
                        SQLcommand sqLcommand = FXMLparser.decode_client_querry(xml);

                        //System.out.println("Command "+sqLcommand.getCommand());
                        String response = dbHandler.sql_executor(sqLcommand);
                        //System.out.println("ответ "+ response);


                        printWriter.println("start");
                        printWriter.println(response);
                        printWriter.println("end");
                        printWriter.flush();


                    }

                    str="AS";
                }
              //  System.out.println("Закрываю соединение");
                Platform.runLater(new Runnable(){
                    @Override
                    public  void  run()
                    {
                        Controller.observableList.remove(socket.toString());
                    }
                    ///
                });
            try {
               // dbHandler.close();
            }
            catch (Exception e2) {
                    e2.printStackTrace();
                }
            try {

                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }catch (Exception e)
        {
            System.out.println("Закрываю");
            e.printStackTrace();
            if (Controller.observableList.contains(socket.toString()))
            {
                Platform.runLater(new Runnable(){
                    @Override
                    public  void  run()
                    {
                        Controller.observableList.remove(socket.toString());
                    }
                    ///
                });
            }
            try {
                socket.close();
            } catch (IOException e1) {
                Platform.runLater(new Runnable(){
                    @Override
                    public  void  run()
                    {
                        Controller.observableList.remove(socket.toString());
                    }
                    ///
                });
                e1.printStackTrace();
            }
        }
    }
}
