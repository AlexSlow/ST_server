package DataBase.users.Rows;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class Row {
    public  abstract ArrayList<ArrayList<String>> getRows(ArrayList<ArrayList<String>> row,ResultSet resultSet);
}
