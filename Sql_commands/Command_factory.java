package Sql_commands;

public class Command_factory {

    public SQLcommand getCommand(String type,String sql,String command)
    {
        SQLcommand sqLcommand=new SQLcommand();
        sqLcommand.setSql(sql);
        sqLcommand.setCommand(command);
        switch (type) {
            case "querry": {
              sqLcommand.setUpdate(false);
              break;
            }
            case "update": {
                sqLcommand.setUpdate(true);
                break;
            }

            default:
                return null;
        }
        return sqLcommand;
    }
}
