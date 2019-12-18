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
    /*
    public void signUp(String user,String password,String mail) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        String insert="Insert INTO users (user_id,user_name,user_password,mail) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement;
        preparedStatement = getDbConnection().prepareStatement(insert);
        preparedStatement.setString(1,null);
        preparedStatement.setString(2,user);
        preparedStatement.setString(3,password);
        preparedStatement.setString(4,mail);
        preparedStatement.execute();

        dbConnection.close();

    }
    */
    /*
    public void Login(String user,String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Statement statement=getDbConnection().createStatement();
        String query="Select user_id,user_name from users where user_name="+"'"+user+"'"+" AND user_password="+"'"+password+"'";
        ResultSet resultSet=statement.executeQuery(query);


        while (resultSet.next())
        {
            User client=new User();
            client.setId(resultSet.getInt("user_id"));
            client.setLogin(resultSet.getString("user_name"));
            return  client;
        }

       // return  null;
    }
    */
    public  void close() throws SQLException {
        dbConnection.close();
    }
    public  String sql_executor(SQLcommand sqLcommand) throws ClassNotFoundException,
            SQLException, InstantiationException, IllegalAccessException {
        if (sqLcommand.getCommand()!=null)
        {
            String xml;
            ArrayList<ArrayList<String>> params=new ArrayList<>();
            Statement statement=getDbConnection().createStatement();
            if (sqLcommand.isUpdate()) //Если это комманда изменения
            {
               int i= statement.executeUpdate(sqLcommand.getCommand());


              if (i!=0)
              {

                xml= FXMLparser.response(sqLcommand.getCommand_text(),params,"sucsess",sqLcommand.getCollumns());
              }
              else
              {
                  xml= FXMLparser.response(sqLcommand.getCommand_text(),params,"error",sqLcommand.getCollumns());
              }
            }else // Иначе если  это выборка
            {
                //System.out.println("Запрос "+sqLcommand.getCommand());
                ResultSet resultSet=statement.executeQuery(sqLcommand.getCommand());
               params = sqLcommand.getRow().getRows(params,resultSet);
               //System.out.println("Параметры "+params);
               xml=FXMLparser.response(sqLcommand.getCommand_text(),params,"sucsess",sqLcommand.getCollumns());

            }
           return xml;
        }

        return  null;
    }
}
