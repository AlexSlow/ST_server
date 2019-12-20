package Sql_commands;

import DataBase.RowParser.Select_user_row;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnknownQuerry extends SQLcommand {
    public UnknownQuerry()
    {
        command="unknown";
    }

    @Override
    public    String getCommand()
    {
        return "";
    }
    @Override
    public void InputInTemplate(PreparedStatement preparedStatement) throws SQLException {
    }
}
