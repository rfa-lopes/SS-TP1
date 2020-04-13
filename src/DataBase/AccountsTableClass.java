package DataBase;

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

public class AccountsTableClass implements AccountsTableInterface {

    private TableInterface table;

    private static final String ROOT_USERNAME      = "root";
    private static final String ROOT_PASSWORD      = "toor";

    private static final String TABLE_NAME      = "accounts";
    private static final String PRIMARY_KEY     = "username";

    private static final String COL_PASSWORD_HASH   = "passwordhash";
    private static final String COL_ISLOGGEDIN      = "isloggedin";
    private static final String COL_ISLOCKED        = "islocked";
    private static final String COL_USER_TYPE       = "usertype";

    private static final String FALSE   = "0";
    private static final String TRUE    = "1";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

        insertAccount(ROOT_USERNAME, ROOT_PASSWORD, UserType.ADMIN);
        Log.info("Insert into: "+ROOT_USERNAME+":"+ROOT_PASSWORD);
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

        Account acc = new Account();
        try {
            ResultSet result = table.getOne(username);

            if(!result.next())
                throw new AccountDoesNotExistsException(username);

            acc.setUsername(result.getString(1));
            acc.setPasswordhash(result.getString(2));
            acc.setIsloggedin(result.getBoolean(3));
            acc.setIslocked(result.getBoolean(4));
            acc.setType(result.getString(5));

        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
        return acc;
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
    public void locked(String username) throws AccountDoesNotExistsException {
        try {
            table.update(username, COL_ISLOCKED, TRUE);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

    @Override
    public void unlocked(String username) throws AccountDoesNotExistsException {
        try {
            table.update(username, COL_ISLOCKED, FALSE);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException(username);
        }
    }

}
