package DataBase;

import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;

public interface AccountsTableInterface {

    void insertAccount(String username, String password) throws AccountAlreadyExistsException;

    void deleteAccount(String username) throws AccountDoesNotExistsException;

    void changePassword(String username, String newPassword) throws AccountDoesNotExistsException;

}
