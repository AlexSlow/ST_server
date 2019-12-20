package Sql_commands;

import DataBase.RowParser.Select_game_row;
import DataBase.RowParser.Select_user_row;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Get_game_command  extends  SQLcommand{
    public Get_game_command()
    {
        this.isUpdate=false;
        command="get_game";
        row=new Select_game_row();
    }
    @Override
    public    String getCommand()
    {
        return "Select * from `game`  where user_id = ? AND (`Date` between ? AND ?)";
    }
    @Override
    public void InputInTemplate(PreparedStatement preparedStatement) throws SQLException {
        //System.out.println(params);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        int id=Integer.parseInt(params.get(0));
        preparedStatement.setInt(1,id);
        try {
            Date date_start=format.parse(params.get(1));
            Date date_end=format.parse(params.get(2));
            preparedStatement.setDate(2,convert(date_start));
            preparedStatement.setDate(3,convert(date_end));
           // System.out.println(preparedStatement);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static java.sql.Date convert(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}
