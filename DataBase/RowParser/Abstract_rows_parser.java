package DataBase.RowParser;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class Abstract_rows_parser {
    public  abstract ArrayList<ArrayList<String>> getRows(ArrayList<ArrayList<String>> row,ResultSet resultSet);
    public  abstract  ArrayList<String> getCollumns();
}
