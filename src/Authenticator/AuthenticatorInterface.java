package Authenticator;

import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.LoginFailsException;
import Exceptions.PasswordDoesNotMatchException;
import Models.Account;
import io.jsonwebtoken.SignatureException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticatorInterface {

    void create_account(String name, String pwd1, String pwd2) throws PasswordDoesNotMatchException, AccountAlreadyExistsException;
    void delete_account(String name) throws AccountDoesNotExistsException;
    Account get_account(String name) throws AccountDoesNotExistsException;
    void change_pwd(String name, String pwd1, String pwd2) throws AccountDoesNotExistsException, LoginFailsException;
    Account login(String name, String pwd) throws AccountDoesNotExistsException, LoginFailsException;
    void logout(Account acc) throws AccountDoesNotExistsException;
    Account login(HttpServletRequest req, HttpServletResponse resp) throws LoginFailsException, SignatureException;

}
