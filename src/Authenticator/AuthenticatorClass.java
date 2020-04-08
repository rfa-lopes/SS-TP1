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
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;

import static javax.management.remote.JMXConnectorServer.AUTHENTICATOR;

public class AuthenticatorClass implements AuthenticatorInterface{

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
    public void create_account(String name, String pwd1, String pwd2) throws PasswordDoesNotMatchException, AccountAlreadyExistsException {

        if(!pwd1.equals(pwd2))
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

        if(!acc.getPasswordhash().equals(passwordhash))
            throw new LoginFailsException();

        accountsTable.login(name);

        return acc;
    }

    @Override
    public void logout(Account acc) throws AccountDoesNotExistsException {
        accountsTable.logout(acc.getUsername());
    }

    @Override
    public Account login(HttpServletRequest req, HttpServletResponse resp) throws AccountDoesNotExistsException, LoginFailsException {
        HttpSession session = req.getSession(true);

        String jwt = (String) session.getAttribute("JWT");

        if(jwt == null)
            throw new LoginFailsException();

        Claims claims = JwtUtil.parseJWT(jwt);

        String jti = claims.getId();
        String iss = claims.getIssuer();
        String sub = claims.getSubject();
        Date exp = claims.getExpiration();

        if(!iss.equals(JwtUtil.ISS) || !sub.equals(JwtUtil.SUB) || !exp.after(new Date()))
            throw new LoginFailsException();

        Account acc = get_account(jti);

        if(!acc.isIsloggedin() || acc.isIslocked())
            throw new LoginFailsException();

        return acc;
    }

}
