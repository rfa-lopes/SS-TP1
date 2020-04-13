package Authenticator;

import DataBase.AccountsTableClass;
import DataBase.AccountsTableInterface;
import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.LoginFailsException;
import Exceptions.PasswordDoesNotMatchException;
import Models.Account;
import Models.UserType;
import Utils.Hash;
import Utils.JwtUtil;
import Utils.Log;
import io.jsonwebtoken.SignatureException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticatorClass implements AuthenticatorInterface {

    AccountsTableInterface accountsTable;

    private AuthenticatorClass() {
        try {
            accountsTable = new AccountsTableClass();
        } catch (AccountAlreadyExistsException e) {
        }
    }

    private static AuthenticatorClass ourInstance = new AuthenticatorClass();

    public static AuthenticatorClass getInstance() {
        return ourInstance;
    }

    @Override
    public void create_account(String name, String pwd1, String pwd2) throws PasswordDoesNotMatchException, AccountAlreadyExistsException {

        if (!pwd1.equals(pwd2))
            throw new PasswordDoesNotMatchException();

        accountsTable.insertAccount(name, pwd1, UserType.ACCOUNT);

    }

    @Override
    public void delete_account(String name) throws AccountDoesNotExistsException {
        accountsTable.deleteAccount(name);
    }

    @Override
    public Account get_account(String name) throws AccountDoesNotExistsException {
        return accountsTable.getAccount(name);
    }

    @Override
    public void change_pwd(String name, String pwd1, String pwd2) throws AccountDoesNotExistsException, LoginFailsException {
        this.login(name, pwd1);

        accountsTable.changePassword(name, pwd2);
    }

    @Override
    public Account login(String name, String pwd) throws AccountDoesNotExistsException, LoginFailsException {
        Account acc = this.get_account(name);

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
    public Account login(HttpServletRequest req, HttpServletResponse resp) throws LoginFailsException, SignatureException {

        String jwt = getCookie(req);

        if (jwt == null)
            throw new LoginFailsException();

        String username = JwtUtil.parseJWT(jwt);

        Account acc = null;
        try {
            acc = get_account(username);
        } catch (AccountDoesNotExistsException e) {
            Log.error("Authenticated account does not exist.");
            throw new LoginFailsException();
        }

        if (!acc.isIsloggedin() || acc.isIslocked())
            throw new LoginFailsException();

        return acc;
    }


    private String getCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if(cookies == null)
            return null;

        String cookieValue = null;

        for (Cookie cookie : cookies)
            if (cookie.getName().equals("Authorization")) {
                cookieValue = cookie.getValue();
                break;
            }
        return cookieValue;
    }

}
