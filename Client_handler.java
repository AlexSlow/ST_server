import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client_handler  implements Runnable {
    private Socket socket;
    DBHandler dbHandler;

    public Client_handler(Socket s,DBHandler dbHandler) {
        socket = s;
        this.dbHandler=dbHandler;
    }

    @Override
    public void run() {
        try( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String str;
                while ((str = bufferedReader.readLine())!=null) {

                    System.out.println(str);
                }

        }catch (Exception e)
        {
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
