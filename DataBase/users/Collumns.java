package DataBase.users;

import java.util.ArrayList;

public class Collumns {
    public  static ArrayList<String> get_users_collumns()
    {
        ArrayList<String> collumns=new ArrayList<>();
        collumns=new ArrayList<>();
        collumns.add("user_id");
        collumns.add("user_name");
        collumns.add("mail");
        return  collumns;
    }
}
