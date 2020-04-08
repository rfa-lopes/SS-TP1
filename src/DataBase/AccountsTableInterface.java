package DataBase;

import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;
import Models.Account;
import Models.UserType;

public interface AccountsTableInterface {

    void insertAccount(String username, String password, UserType type) throws AccountAlreadyExistsException;

    void deleteAccount(String username) throws AccountDoesNotExistsException;

    void changePassword(String username, String newPassword) throws AccountDoesNotExistsException;

    Account getAccount(String username) throws AccountDoesNotExistsException;

    void login(String username) throws AccountDoesNotExistsException;

    void logout(String username) throws AccountDoesNotExistsException;

    void locked(String username) throws AccountDoesNotExistsException;

    void unlocked(String username) throws AccountDoesNotExistsException;
}
