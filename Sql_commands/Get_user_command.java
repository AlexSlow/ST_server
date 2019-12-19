package Sql_commands;

import DataBase.RowParser.Select_user_row;

public class Get_user_command extends SQLcommand {
    public Get_user_command()
    {
        this.isUpdate=false;
        command="get_user";
        row=new Select_user_row();
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
