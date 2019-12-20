package Sql_commands;

import DataBase.RowParser.Select_user_row;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Get_user_command extends SQLcommand {

    public Get_user_command() {
        this.isUpdate = false;
        command = "get_user";
        row = new Select_user_row();
    }

    @Override
    public String getCommand() {
        return "Select * from users where user_name = ? AND user_password = ?";
    }

    @Override
    public void InputInTemplate(PreparedStatement preparedStatement) throws SQLException {
        //System.out.println(params);
        preparedStatement.setString(1, params.get(0));
        preparedStatement.setString(2, params.get(1));

    }
}

