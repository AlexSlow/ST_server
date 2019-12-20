import DataBase.DBHandler;
import DataBase.FXMLparser;
import Sql_commands.SQLcommand;

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

    }

    @Override
    public void run() {
        try( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String xml,str;
            printWriter=new PrintWriter(socket.getOutputStream());
                while ((str = bufferedReader.readLine())!=null) {

                        if (str.equals("start"))
                        {
                            str="";
                            xml="";
                            while (!((str = bufferedReader.readLine()).equals("end")))
                            {
                                xml=xml+str+"\n";
                            }


                            //System.out.println("от клиента " + xml);
                           SQLcommand sqLcommand= FXMLparser.decode_client_querry(xml);

                            //System.out.println("Command "+sqLcommand.getCommand());
                            String response=dbHandler.sql_executor(sqLcommand);
                           System.out.println("ответ "+ response);
                            printWriter.println("start");
                            printWriter.println(response);
                            printWriter.println("end");
                            printWriter.flush();


                        }


                }

            str=bufferedReader.readLine();
            //System.out.println(str);

        }catch (Exception e)
        {
            e.printStackTrace();
            //System.out.println("Закрываю");
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
