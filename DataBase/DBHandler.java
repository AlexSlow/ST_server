package DataBase;

import Sql_commands.SQLcommand;

import java.sql.*;
import java.util.ArrayList;

public class DBHandler  {
    Connection dbConnection;

    public  Connection getDbConnection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost/shulte_taqble_db?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "root";
        String password = "543210";
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Connection dbConnection = DriverManager.getConnection(url, username, password);
        return  dbConnection;
    }

    public  void close() throws SQLException {
        dbConnection.close();
    }


    public  String sql_executor(SQLcommand sqLcommand) {
        if (sqLcommand.getCommand()!=null)
        {
            String xml;
            ArrayList<ArrayList<String>> params=new ArrayList<>();
            Statement statement= null;
            try {
                statement = getDbConnection().createStatement();
            } catch (Exception e) {
                e.printStackTrace();
                xml=FXMLparser.response(sqLcommand.getCommand_text(),params,"error",sqLcommand.getCollumns());
            }
            if (sqLcommand.isUpdate()) //Если это комманда изменения
            {
                try {
                    int i= statement.executeUpdate(sqLcommand.getCommand());
                    xml=FXMLparser.response(sqLcommand.getCommand_text(),params,"sucsess",sqLcommand.getCollumns());
                } catch (SQLException e) {
                    xml=FXMLparser.response(sqLcommand.getCommand_text(),params,"error",sqLcommand.getCollumns());
                    e.printStackTrace();
                }

            }else // Иначе если  это выборка
            {
                //System.out.println("Запрос "+sqLcommand.getCommand());
                ResultSet resultSet= null;
                try {
                    resultSet = statement.executeQuery(sqLcommand.getCommand());
                    params = sqLcommand.getRow().getRows(params,resultSet);
                    //System.out.println("Параметры "+params);
                    xml=FXMLparser.response(sqLcommand.getCommand_text(),params,"sucsess",sqLcommand.getCollumns());
                } catch (SQLException e) {
                    xml=FXMLparser.response(sqLcommand.getCommand_text(),params,"error",sqLcommand.getCollumns());
                    e.printStackTrace();
                }


            }
           return xml;
        }

        return  null;
    }
}
