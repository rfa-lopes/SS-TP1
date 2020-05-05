package Authenticator;

import Models.Logger;
import Models.Operation;

import java.util.List;

public interface LoggerInterface {
    void insertLogger(String username, Operation operation);

    List<Logger> getLoggers();
}
