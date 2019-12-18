package Sql_commands;

import DataBase.users.Collumns;
import DataBase.users.Rows.User_row;

public class Set_user_command extends  SQLcommand {
    public Set_user_command()
    {
        this.isUpdate=true;
        command="set_user";
        row=new User_row();
        collumns= Collumns.get_users_collumns();
    }

    @Override
    public    String getCommand()
    {
        if ((params!=null)&&(params.size()==4)) {
            return "Insert INTO users (user_id,user_name,user_password,mail) VALUES("
                    +params.get(0)+" "+params.get(1)+" "+params.get(2)+" "+params.get(3)+")";
        }
        return null;
    }

}
