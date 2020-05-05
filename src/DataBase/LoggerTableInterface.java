package DataBase;

import Models.Logger;
import Models.Operation;
import java.util.List;

public interface LoggerTableInterface {
    void insertLogger(String username, Operation operation);
    List<Logger> getLoggers();
}
