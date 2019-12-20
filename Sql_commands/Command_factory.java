package Sql_commands;

public class Command_factory {
    public SQLcommand getCommand(String command)
    {
        switch (command) {
            case "set_user": {
            return  new Set_user_command();
            }
            case "get_user": {
                return  new Get_user_command();
            }
            case "set_game": {
                return  new Set_game_command();
            }
            case "get_game": {
                return  new Get_game_command();
            }
            default:
                return new UnknownQuerry();
        }
    }
}
