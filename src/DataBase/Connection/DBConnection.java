package DataBase.Connection;

import Utils.Log;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static final String DB_CONN = "jdbc:sqlite:";
    public static final String DB_NAME = "database.db";

    /*Tomcat tests*/
    public static final String DB_PATH = "../webapps/SS-TP1/WEB-INF/database/";

    /*Local tests*/
    //public static final String DB_PATH = "WEB-INF/database/";

    private static Connection conn;

    public static Connection getInstance() {
        if (conn == null)
            try {
                File db = new File(DB_PATH + DB_NAME);
                if (db.createNewFile())
                    Log.initial("Creating new data base file in: %s", DB_PATH + DB_NAME);
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(DB_CONN + DB_PATH + DB_NAME);
                Log.initial("Create connection to database in: " + DB_PATH + DB_NAME);
            } catch (SQLException | IOException | ClassNotFoundException e) {
                Log.error("Data base connection fail.");
                System.exit(9);
            }
        return conn;
    }

}
