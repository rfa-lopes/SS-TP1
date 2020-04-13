package Authenticator;

import Exceptions.*;
import Models.Account;
import io.jsonwebtoken.SignatureException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticatorInterface {

    void create_account(String name, String pwd1, String pwd2) throws PasswordDoesNotMatchException, AccountAlreadyExistsException, EmptyInputException;
    void delete_account(String name) throws AccountDoesNotExistsException, EmptyInputException;
    Account get_account(String name) throws AccountDoesNotExistsException, EmptyInputException;
    void change_pwd(String name, String pwd1, String pwd2) throws AccountDoesNotExistsException, LoginFailsException, EmptyInputException;
    Account login(String name, String pwd) throws AccountDoesNotExistsException, LoginFailsException, EmptyInputException;
    void logout(Account acc) throws AccountDoesNotExistsException;
    Account login(HttpServletRequest req, HttpServletResponse resp) throws LoginFailsException, SignatureException, IOException;

}
