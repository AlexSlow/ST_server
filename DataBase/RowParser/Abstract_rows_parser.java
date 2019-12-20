package DataBase.RowParser;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class Abstract_rows_parser {
    protected ArrayList<String> collumn;
    public  ArrayList<String> getCollumns(){return  collumn;}
    public  abstract ArrayList<ArrayList<String>> getRows(ResultSet resultSet);
}
