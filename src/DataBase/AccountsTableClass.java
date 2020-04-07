package DataBase;

import DataBase.Table.AttributeTypeEnum;
import DataBase.Table.TableClass;
import DataBase.Table.TableInterface;
import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;
import Models.UserType;
import Utils.Hash;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountsTableClass implements AccountsTableInterface {

    private TableInterface table;

    private static final String TABLE_NAME      = "accounts";
    private static final String PRIMARY_KEY     = "username";

    private static final String COL_PASSWORD_HASH   = "passwordhash";
    private static final String COL_LAST_LOGGIN     = "lastloggin";
    private static final String COL_USER_TYPE       = "usertype";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public AccountsTableClass() {
        table = new TableClass(TABLE_NAME);
        table.drop();
        table.addPrimaryKey(PRIMARY_KEY, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_PASSWORD_HASH, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_LAST_LOGGIN, AttributeTypeEnum.VARCHAR)
                .addNotNullAttribute(COL_USER_TYPE, AttributeTypeEnum.VARCHAR)
                .create();
    }

    @Override
    public void insertAccount(String username, String password) throws AccountAlreadyExistsException {
        String passwordhas = Hash.get(password, username);
        String lastloggin = new SimpleDateFormat(DATE_FORMAT).format(new Date(0));
        String usertype = UserType.ACCOUNT.name();

        try {
            table.insert(username, passwordhas, lastloggin, usertype);
        } catch (SQLException e) {
            throw new AccountAlreadyExistsException();
        }
    }

    @Override
    public void deleteAccount(String username) throws AccountDoesNotExistsException {
        try {
            table.remove(username);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException();
        }
    }

    @Override
    public void changePassword(String username, String newPassword) throws AccountDoesNotExistsException {
        try {
            table.update(username, COL_PASSWORD_HASH, newPassword);
        } catch (SQLException e) {
            throw new AccountDoesNotExistsException();
        }
    }


}
