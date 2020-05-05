package Authenticator;

import DataBase.LoggerTableClass;
import DataBase.LoggerTableInterface;
import Models.Logger;
import Models.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LoggerClass implements LoggerInterface{

    private LoggerTableInterface loggerTable;

    private static LoggerClass ourInstance = new LoggerClass();

    public static LoggerClass getInstance() {
        return ourInstance;
    }

    private LoggerClass(){
        loggerTable = new LoggerTableClass();
    }

    @Override
    public void insertLogger(String username, Operation operation) {
        loggerTable.insertLogger(username, operation);
    }

    @Override
    public List<Logger> getLoggers() {
        return loggerTable.getLoggers();
    }



}
