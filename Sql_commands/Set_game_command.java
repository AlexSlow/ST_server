package Sql_commands;

import DataBase.RowParser.Select_user_row;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Set_game_command extends SQLcommand {
    public Set_game_command()
    {
        this.isUpdate=true;
        command="set_game";
        //row=new Select_user_row();
    }

    @Override
    public    String getCommand()
    {
        return "INSERT INTO `shulte_taqble_db`.`game` (`user_id`, `Time`, `Date`, `Points`, `Numbers`, `Errors`,`mode`)" +
                " VALUES (?, ?, NOW(), ?, ?, ?,?);";
    }
    @Override
    public void InputInTemplate(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,params.get(0));
        preparedStatement.setString(2,params.get(1));
        preparedStatement.setString(3,params.get(2));
        preparedStatement.setString(4,params.get(3));
        preparedStatement.setString(5,params.get(4));
        preparedStatement.setString(6,params.get(5));
       // System.out.println(preparedStatement);
    }
}
