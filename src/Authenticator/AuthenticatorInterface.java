package Authenticator;

import Exceptions.*;
import Models.Account;
import Models.Logger;
import io.jsonwebtoken.SignatureException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface AuthenticatorInterface {

    void create_account(String name, String pwd1, String pwd2) throws PasswordDoesNotMatchException, AccountAlreadyExistsException, EmptyInputException, WeakPasswordException;
    void delete_account(String name) throws AccountDoesNotExistsException, EmptyInputException;
    Account get_account(String name) throws AccountDoesNotExistsException, EmptyInputException;
    List<Account> get_accounts();
    void change_pwd(String name, String pwd1, String pwd2) throws AccountDoesNotExistsException, LoginFailsException, EmptyInputException, PasswordDoesNotMatchException, WeakPasswordException;
    Account login(String ip, String name, String pwd) throws AccountDoesNotExistsException, LoginFailsException, EmptyInputException, ExceedNrTriesException;
    void logout(Account acc) throws AccountDoesNotExistsException;
    Account login(HttpServletRequest req, HttpServletResponse resp) throws LoginFailsException, SignatureException, IOException;
    void lock(String name) throws AccountDoesNotExistsException, EmptyInputException, IsAdminException;
    void checkPassword(String name, String pwd) throws EmptyInputException, AccountDoesNotExistsException, LoginFailsException;
}
