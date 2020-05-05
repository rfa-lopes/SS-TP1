package Tests;

import DataBase.Connection.MariaDBConnection;

import java.sql.Connection;

public class TestDBConnection {

    public static void main(String[] args){

        Connection conn = MariaDBConnection.getInstance();

    }

}

