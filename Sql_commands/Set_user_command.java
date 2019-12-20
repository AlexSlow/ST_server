package Sql_commands;

import DataBase.RowParser.Select_user_row;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Set_user_command extends  SQLcommand {
    public Set_user_command()
    {
        this.isUpdate=true;
        command="set_user";
        row=new Select_user_row();
    }

    @Override
    public    String getCommand()
    {
            return "Insert INTO users (user_id,user_name,user_password,mail) VALUES(null, ?, ?, ?)";
    }
    @Override
    public void InputInTemplate(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,params.get(0));
        preparedStatement.setString(2,params.get(1));
        preparedStatement.setString(3,params.get(2));
    }

}
