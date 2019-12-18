package Sql_commands;
import DataBase.users.Rows.Row;

import java.util.ArrayList;

public  abstract  class SQLcommand {
    protected Row row;
    protected   String command;
    protected  boolean isUpdate;
    protected ArrayList<String> params;
    protected ArrayList<String> collumns;
    public  SQLcommand(){}
    public ArrayList<String> getParams() {
        return params;
    }
    public  abstract  String getCommand();

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

    public Row getRow() {
        return row;
    }

    public ArrayList<String> getCollumns() {
        return collumns;
    }
}
