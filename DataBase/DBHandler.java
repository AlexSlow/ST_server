package DataBase;

import Handlers.Controller;
import Settings.Logging;
import Sql_commands.SQLcommand;

import javax.swing.text.html.ListView;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static res.RESURSE.INI_FILE;

public class DBHandler {
    private Connection dbConnection;



    public Connection getDbConnection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
       // Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost/shulte_taqble_db?serverTimezone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "543210";
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Connection dbConnection = DriverManager.getConnection(url, username, password);
        return dbConnection;
    }

    public void close() throws SQLException {
        Logger l=Logger.getGlobal();
        l.warning("Закрываю соединение с БД");
        if (!dbConnection.isClosed()) {
            dbConnection.close();
        }
    }

// Выполнить запрос
    /**
     * The HelloWorld program implements an application that
     * simply displays "Hello World!" to the standard output.
     *@param sqLcommand класс хранящий sql запрос
     * @return файл xml-ответ
     * @author  Терехов А.С
     * @version 1.0
     */
    public String sql_executor(SQLcommand sqLcommand) throws SQLException {
        String xml;
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        if (sqLcommand != null) {

            PreparedStatement statement = null;
            try {
                statement = getDbConnection().prepareStatement(sqLcommand.getSql());

                //Если  это добавление
                if (sqLcommand.isUpdate()) //Если это комманда изменения
                {

                    sqLcommand.InputInTemplate(statement);
                    statement.executeUpdate();
                    xml = FXMLparser.response(sqLcommand.getCommand(), params, "sucsess", null);


                } else // Иначе если  это выборка
                {
                    ResultSet resultSet = null;
                    sqLcommand.InputInTemplate(statement);
                    resultSet = statement.executeQuery();
                    params = SQLcommand.getRows(resultSet);
                    xml = FXMLparser.response(sqLcommand.getCommand(), params, "sucsess", SQLcommand.getCollumns(resultSet));

                }

            } catch (Exception e) {
                e.printStackTrace();
                xml = FXMLparser.response(sqLcommand.getCommand(), params, "error", null);
            }


        }else
        {
            Logging logging=new Logging();
            try {
                logging.getFileLogger(this.getClass()).warning("Пустой запрос для базы данных");
            } catch (IOException e) {
                e.printStackTrace();
            }
            xml = FXMLparser.response(sqLcommand.getCommand(), params, "error", null);
        }
        return xml;
    }
}


