package DataBase.Connection;

import Config.Configs;
import Utils.Log;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MariaDBConnection {
    private static final String DATABASE_NAME = "AUTHENTICATOR";

    private static final String DB_CONN = "jdbc:mysql://localhost/" + DATABASE_NAME;

    private static Connection conn;

    public static Connection getInstance() {
        if (conn == null)
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_CONN, Configs.MARIA_USER, Configs.MARIA_PASSWORD);
                Statement stmt = conn.createStatement();
                String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
                stmt.executeUpdate(sql);
                stmt.close();
            } catch (Exception e) {
                Log.error("Data base connection fail. (USE_MARIA)");
                e.printStackTrace();
                System.exit(9);
            }
        return conn;
    }

}
