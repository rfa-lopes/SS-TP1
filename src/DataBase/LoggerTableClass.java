package DataBase;

import Authenticator.AuthenticatorClass;
import DataBase.Table.AttributeTypeEnum;
import DataBase.Table.TableClass;
import DataBase.Table.TableInterface;
import Models.Logger;
import Models.Operation;
import Utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LoggerTableClass implements LoggerTableInterface{

    private TableInterface table;

    private static final String TABLE_NAME      = "loggers";

    private static final String PRIMARY_KEY     = "id";
    private static final String COL_USERNAME    = "username";
    private static final String COL_OPERATION   = "operation";
    private static final String COL_DATE        = "date";

    private static long countId;

    public LoggerTableClass() {

        table = new TableClass(TABLE_NAME);
        table.drop();
        Log.initial("Drop table: " + TABLE_NAME);
        table.addPrimaryKey(PRIMARY_KEY, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_USERNAME, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_OPERATION, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_DATE, AttributeTypeEnum.VARCHAR)
                .create();
        Log.initial("Create table: " + TABLE_NAME);
        countId = 0;
    }

    @Override
    public void insertLogger(String username, Operation operation) {

        String pattern = "dd-MM-yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        try {
            table.insert(String.valueOf(countId++), username, operation.name(), date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Logger> getLoggers() {
        List<Logger> list = new LinkedList<>();
        try {
            ResultSet result = table.getAll();
            while(result.next())
                list.add(getLoggerFromResult(result));
        } catch (SQLException e) {}
        return list;
    }

    private Logger getLoggerFromResult(ResultSet result) throws SQLException {
        Logger log = new Logger();
        log.setId(result.getString(1));
        log.setUsername(result.getString(2));
        log.setOpertation(result.getString(3));
        log.setDate(result.getString(4));
        return log;
    }
}
