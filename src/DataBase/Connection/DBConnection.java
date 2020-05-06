package DataBase.Connection;

import Config.Configs;
import java.sql.Connection;
public class DBConnection {

    public static Connection getInstance() {

        if(Configs.USE_MARIA)
            return MariaDBConnection.getInstance();
        else
            return SQLiteConnection.getInstance();
    }

}
