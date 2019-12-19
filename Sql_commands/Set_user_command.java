package Sql_commands;

import DataBase.RowParser.Select_user_row;

public class Set_user_command extends  SQLcommand {
    public Set_user_command()
    {
        this.isUpdate=true;
        command="set_user";
        row=new Select_user_row();
    }

    @Override
    public    String getCommand()
    {
        if ((params!=null)&&(params.size()==3)) {
            return "Insert INTO users (user_id,user_name,user_password,mail) VALUES(null"
                   +", "+params.get(0)+", "+params.get(1)+", "+params.get(2)+")";
        }
        return null;
    }

}
