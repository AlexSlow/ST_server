package DataBase.RowParser;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Select_game_row extends  Abstract_rows_parser {

    public    Select_game_row()
    {
        ArrayList<String> collumns=new ArrayList<>();
        collumns=new ArrayList<>();
        collumns.add("game_id");
        collumns.add("user_id");
        collumns.add("Time");

        collumns.add("Date");
        collumns.add("Points");
        collumns.add("Numbers");

        collumns.add("Errors");
        collumns.add("mode");
        this.collumn=collumns;
    }
    @Override
    public   ArrayList<ArrayList<String>> getRows(ResultSet resultSet)
    {
        ArrayList<ArrayList<String>> rows=new ArrayList<>();
        try {
            while(resultSet.next())
            {
                ArrayList<String> param=new ArrayList<>();
                for (int i=0;i<getCollumns().size();i++)
                {
                    param.add(resultSet.getString(getCollumns().get(i)));
                }
                rows.add(param);

            }
            return rows;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
