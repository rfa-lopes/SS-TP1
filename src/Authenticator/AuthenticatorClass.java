package Authenticator;

import DataBase.AccountsTableClass;
import DataBase.AccountsTableInterface;
import Exceptions.*;
import Models.Account;
import Models.UserType;
import Utils.Cookies;
import Utils.Hash;
import Utils.JwtUtil;
import Utils.Log;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static Utils.JwtUtil.*;

public class AuthenticatorClass implements AuthenticatorInterface {

    AccountsTableInterface accountsTable;

    private AuthenticatorClass() {
        try {
            accountsTable = new AccountsTableClass();
        } catch (AccountAlreadyExistsException e) { }
    }

    private static AuthenticatorClass ourInstance = new AuthenticatorClass();

    public static AuthenticatorClass getInstance() {
        return ourInstance;
    }

    @Override
    public void create_account(String name, String pwd1, String pwd2) throws PasswordDoesNotMatchException, AccountAlreadyExistsException, EmptyInputException {

        if(name.equals("") || pwd1.equals("") || pwd2.equals("") )
            throw new EmptyInputException();

        if (!pwd1.equals(pwd2))
            throw new PasswordDoesNotMatchException();

        accountsTable.insertAccount(name, pwd1, UserType.ACCOUNT);

    }

    @Override
    public void delete_account(String name) throws AccountDoesNotExistsException, EmptyInputException {

        if(name.equals(""))
            throw new EmptyInputException();

        accountsTable.deleteAccount(name);
    }

    @Override
    public Account get_account(String name) throws AccountDoesNotExistsException, EmptyInputException {
        if(name.equals(""))
            throw new EmptyInputException();
        return accountsTable.getAccount(name);
    }

    @Override
    public List<Account> get_accounts() {
        return accountsTable.getAllAccounts();
    }

    @Override
    public void change_pwd(String name, String pwd1, String pwd2) throws AccountDoesNotExistsException, EmptyInputException, PasswordDoesNotMatchException {
        if(name.equals("") || pwd1.equals("") || pwd2.equals("") )
            throw new EmptyInputException();

        if(!pwd1.equals(pwd2) )
            throw new PasswordDoesNotMatchException();

        accountsTable.changePassword(name, pwd2);
    }

    @Override
    public Account login(String name, String pwd) throws AccountDoesNotExistsException, LoginFailsException, EmptyInputException {
        Account acc = this.get_account(name);

        if(acc.isIslocked())
            throw new LoginFailsException();

        String passwordhash = Hash.get(pwd, name);

        if (!acc.getPasswordhash().equals(passwordhash))
            throw new LoginFailsException(name);

        accountsTable.login(name);
        Log.info("Login success by: " + name);

        return acc;
    }

    @Override
    public void logout(Account acc) throws AccountDoesNotExistsException {
        accountsTable.logout(acc.getUsername());
    }

    @Override
    public Account login(HttpServletRequest req, HttpServletResponse resp) throws LoginFailsException, SignatureException, ExpiredJwtException {

        List<String> tokens = Cookies.getCookiesInString(req, JWT_TYPE);

        if (tokens.size() == 0)
            throw new LoginFailsException();

        String token = tokens.get(0);

        String username = null;
        Account acc = null;

        try {
            username = JwtUtil.parseJWT(token);
            acc = get_account(username);
        } catch (AccountDoesNotExistsException | EmptyInputException e) {
            Log.error("Authenticated account does not exist.");
            throw new LoginFailsException();
        }

        if (!acc.isIsloggedin() || acc.isIslocked())
            throw new LoginFailsException();

        return acc;
    }

    @Override
    public void lock(String name) throws AccountDoesNotExistsException, EmptyInputException, IsAdminException {
        if(name.equals(""))
            throw new EmptyInputException();

        Account acc = get_account(name);

        if(acc.getType().equalsIgnoreCase(UserType.ADMIN.name()))
            throw new IsAdminException();

        if(acc.isIslocked())
            accountsTable.setUnlocked(name);
        else
            accountsTable.setLocked(name);
    }

}
