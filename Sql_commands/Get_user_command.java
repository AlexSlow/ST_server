package Sql_commands;

import DataBase.users.Collumns;
import DataBase.users.Rows.User_row;

public class Get_user_command extends SQLcommand {
    public Get_user_command()
    {
        this.isUpdate=false;
        command="get_user";
        row=new User_row();
        collumns= Collumns.get_users_collumns();
    }
    @Override
    public    String getCommand()
    {
        if ((params!=null)&&(params.size()==2)) {
            return "Select * from users where user_name="+params.get(0)+" "
                    +"AND user_password="+params.get(1);
        }
        return null;
    }
}
