package Sql_commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public    class SQLcommand {
    protected   String command;
    protected  boolean isUpdate;
    protected ArrayList<String> params;
    protected String sql;

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public void setSql(String SQL)
    {
        sql=SQL;
    }

    public String getSql() {
        return sql;
    }

    public    void InputInTemplate(PreparedStatement preparedStatement) throws SQLException{

        for (int i=0;i<params.size();i++)
        {
            preparedStatement.setString(i+1,params.get(i));
        }
      //  System.out.println(preparedStatement);
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public boolean isUpdate() {
        return isUpdate;
    }
    public  String getCommand()
    {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public  static ArrayList<String> getCollumns(ResultSet resultSet) throws SQLException {
        ArrayList<String> collumns=new ArrayList<>();
            ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
            //System.out.println(resultSetMetaData.getColumnCount());
            for (int i=1;i<=resultSetMetaData.getColumnCount();i++)
            {
                //System.out.println(resultSetMetaData.getColumnLabel(i));
                collumns.add(resultSetMetaData.getColumnLabel(i));
            }
        return collumns;
    }

    public  static   ArrayList<ArrayList<String>> getRows(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();

        ArrayList<ArrayList<String>> result=new ArrayList<>();

        while (resultSet.next())
        {
            ArrayList<String> row=new ArrayList<>();
            for (int i=1;i<=resultSetMetaData.getColumnCount();i++) {
                row.add(resultSet.getString(i));
            }
            result.add(row);
        }
        return  result;
    }
}
