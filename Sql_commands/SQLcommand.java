package Sql_commands;
import DataBase.RowParser.Abstract_rows_parser;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public  abstract  class SQLcommand {
    protected Abstract_rows_parser row; //Для вывода на XML
    protected   String command;
    protected  boolean isUpdate;
    protected ArrayList<String> params;
    public  SQLcommand(){}
    public ArrayList<String> getParams() {
        return params;
    }
    public  abstract  String getCommand();
    public  abstract  void InputInTemplate(PreparedStatement preparedStatement) throws SQLException;

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public boolean isUpdate() {
        return isUpdate;
    }
    public  String getCommand_text()
    {
        return command;
    }

    public Abstract_rows_parser getRow() {
        return row;
    }

    public ArrayList<String> getCollumns() {
        if (row!=null)
        {
            return row.getCollumns();
        }else return null;
    }
}
