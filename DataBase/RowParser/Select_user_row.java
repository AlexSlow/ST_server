package DataBase.RowParser;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Select_user_row extends Abstract_rows_parser {
    @Override
    public   ArrayList<ArrayList<String>> getRows(ArrayList<ArrayList<String>> row,ResultSet resultSet)
    {
        row.clear();
        try {
            while(resultSet.next())
            {
                    ArrayList<String> param=new ArrayList<>();
                    int id=resultSet.getInt(getCollumns().get(0));
                    param.add(Integer.toString(id));
                    param.add(resultSet.getString(getCollumns().get(1)));
                    param.add(resultSet.getString(getCollumns().get(2)));
                    row.add(param);

            }
            return row;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }


    }
    public    ArrayList<String> getCollumns()
    {
        ArrayList<String> collumns=new ArrayList<>();
        collumns=new ArrayList<>();
        collumns.add("user_id");
        collumns.add("user_name");
        collumns.add("mail");
        return  collumns;
    }
}
