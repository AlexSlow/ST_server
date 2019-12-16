import java.sql.*;

public class DBHandler extends Config {
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
    public void Login(String user,String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Statement statement=getDbConnection().createStatement();
        String query="Select user_id,user_name from users where user_name="+"'"+user+"'"+" AND user_password="+"'"+password+"'";
        ResultSet resultSet=statement.executeQuery(query);

        /*
        while (resultSet.next())
        {
            User client=new User();
            client.setId(resultSet.getInt("user_id"));
            client.setLogin(resultSet.getString("user_name"));
            return  client;
        }
        */
       // return  null;
    }
    public  void close() throws SQLException {
        dbConnection.close();
    }
}
