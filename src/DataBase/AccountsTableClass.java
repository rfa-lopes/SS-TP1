package DataBase;

import Config.Configs;
import DataBase.Table.AttributeTypeEnum;
import DataBase.Table.TableClass;
import DataBase.Table.TableInterface;
import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;
import Models.Account;
import Models.UserType;
import Utils.Hash;
import Utils.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AccountsTableClass implements AccountsTableInterface {

    private TableInterface table;

    private static final String TABLE_NAME      = "accounts";
    private static final String PRIMARY_KEY     = "username";

    private static final String COL_PASSWORD_HASH   = "passwordhash";
    private static final String COL_ISLOGGEDIN      = "isloggedin";
    private static final String COL_ISLOCKED        = "islocked";
    private static final String COL_USER_TYPE       = "usertype";

    private static final String FALSE   = "0";
    private static final String TRUE    = "1";

    public AccountsTableClass() throws AccountAlreadyExistsException {
        table = new TableClass(TABLE_NAME);
        table.drop();
        Log.info("Drop table: " + TABLE_NAME);
        table.addPrimaryKey(PRIMARY_KEY, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_PASSWORD_HASH, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_ISLOGGEDIN, AttributeTypeEnum.BOOLEAN)
                .addNotNullAttribute(COL_ISLOCKED, AttributeTypeEnum.BOOLEAN)
                .addNotNullAttribute(COL_USER_TYPE, AttributeTypeEnum.VARCHAR)
                .create();
        Log.info("Create table: " + TABLE_NAME);

        insertAccount(Configs.ROOT_USERNAME, Configs.ROOT_PASSWORD, UserType.ADMIN);
        Log.info("Insert into: "+Configs.ROOT_USERNAME+":"+Configs.ROOT_PASSWORD);
    }

    @Override
    public void insertAccount(String username, String password, UserType type) throws AccountAlreadyExistsException {
        String passwordhash = Hash.get(password, username);

        try {
            table.insert(username, passwordhash, FALSE, FALSE, type.name());
        } catch (SQLException e) {
            throw new AccountAlreadyExistsException();
        }
    }

    @Override
    public void deleteAccount(String username) throws AccountDoesNotExistsException {
        try {
            //table.getOne(username);
            table.remove(username);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    @Override
    public void changePassword(String username, String newPassword) throws AccountDoesNotExistsException {
        String passwordhash = Hash.get(newPassword, username);
        try {
            table.update(username, COL_PASSWORD_HASH, passwordhash);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    @Override
    public Account getAccount(String username) throws AccountDoesNotExistsException {
        try {
            ResultSet result = table.getOne(username);

            if(!result.next())
                throw new AccountDoesNotExistsException(username);

            return getAccountFromResult(result);

        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> list = new LinkedList<>();
        try {
            ResultSet result = table.getAll();
            while(result.next())
                list.add(getAccountFromResult(result));
            return list;
        } catch (SQLException e) {}
        return list;
    }

    @Override
    public void login(String username) throws AccountDoesNotExistsException {
        try {
            table.update(username, COL_ISLOGGEDIN, TRUE);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    @Override
    public void logout(String username) throws AccountDoesNotExistsException {
        try {
            table.update(username, COL_ISLOGGEDIN, FALSE);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    @Override
    public void setLocked(String username) throws AccountDoesNotExistsException {
        try {
            table.update(username, COL_ISLOCKED, TRUE);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    @Override
    public void setUnlocked(String username) throws AccountDoesNotExistsException {
        try {
            table.update(username, COL_ISLOCKED, FALSE);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    private Account getAccountFromResult(ResultSet result) throws SQLException {
        Account acc = new Account();
        acc.setUsername(result.getString(1));
        acc.setPasswordhash(result.getString(2));
        acc.setIsloggedin(result.getBoolean(3));
        acc.setIslocked(result.getBoolean(4));
        acc.setType(result.getString(5));
        return acc;
    }
}
