package DataBase.users.Rows;

import DataBase.users.Collumns;
import DataBase.users.Rows.Row;

import java.sql.ResultSet;
import java.util.ArrayList;

public class User_row extends Row {
    @Override
    public   ArrayList<ArrayList<String>> getRows(ArrayList<ArrayList<String>> row,ResultSet resultSet)
    {
        row.clear();
        try {
            while(resultSet.next())
            {
                    ArrayList<String> param=new ArrayList<>();
                    int id=resultSet.getInt("user_id");
                    param.add(Integer.toString(id));
                    param.add(resultSet.getString("user_name"));
                    param.add(resultSet.getString("mail"));
                    row.add(param);

            }
            return row;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }


    }
}
