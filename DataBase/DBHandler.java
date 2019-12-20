package DataBase;

import Sql_commands.SQLcommand;
import Sql_commands.UnknownQuerry;

import java.sql.*;
import java.util.ArrayList;

public class DBHandler {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost/shulte_taqble_db?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "root";
        String password = "543210";
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        Connection dbConnection = DriverManager.getConnection(url, username, password);
        return dbConnection;
    }

    public void close() throws SQLException {
        dbConnection.close();
    }


    public String sql_executor(SQLcommand sqLcommand) {
        if (sqLcommand.getCommand() != null) {
            String xml;
            ArrayList<ArrayList<String>> params = new ArrayList<>();
            if (sqLcommand instanceof UnknownQuerry) {
                xml = FXMLparser.response(sqLcommand.getCommand_text(), params, "error", sqLcommand.getCollumns());

            }else {

            PreparedStatement statement = null;
            try {
                statement = getDbConnection().prepareStatement(sqLcommand.getCommand());

                //Если  это добавление
                if (sqLcommand.isUpdate()) //Если это комманда изменения
                {

                    sqLcommand.InputInTemplate(statement);
                    statement.executeUpdate();
                    xml = FXMLparser.response(sqLcommand.getCommand_text(), params, "sucsess", sqLcommand.getCollumns());


                } else // Иначе если  это выборка
                {
                    ResultSet resultSet = null;
                    sqLcommand.InputInTemplate(statement);
                    resultSet = statement.executeQuery();
                    params = sqLcommand.getRow().getRows(resultSet);
                    xml = FXMLparser.response(sqLcommand.getCommand_text(), params, "sucsess", sqLcommand.getCollumns());

                }

            } catch (Exception e) {
                e.printStackTrace();
                xml = FXMLparser.response(sqLcommand.getCommand_text(), params, "error", sqLcommand.getCollumns());
            }
        }
            return xml;

        }
        return null;
    }
}


