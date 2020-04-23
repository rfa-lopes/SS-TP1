package DataBase;

import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;
import Models.Account;
import Models.UserType;

import java.util.List;

public interface AccountsTableInterface {

    void insertAccount(String username, String password, UserType type) throws AccountAlreadyExistsException;

    void deleteAccount(String username) throws AccountDoesNotExistsException;

    void changePassword(String username, String newPassword) throws AccountDoesNotExistsException;

    Account getAccount(String username) throws AccountDoesNotExistsException;

    List<Account> getAllAccounts();

    void login(String username) throws AccountDoesNotExistsException;

    void logout(String username) throws AccountDoesNotExistsException;

    void setLocked(String username) throws AccountDoesNotExistsException;

    void setUnlocked(String username) throws AccountDoesNotExistsException;
}
