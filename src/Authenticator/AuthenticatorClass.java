package Authenticator;

import Models.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticatorClass implements AuthenticatorInterface{

    private AuthenticatorClass() {

    }

    private static AuthenticatorClass ourInstance = new AuthenticatorClass();

    public static AuthenticatorClass getInstance() {
        return ourInstance;
    }

    @Override
    public void create_account(String name, String pwd1, String pwd2) {

    }

    @Override
    public void delete_account(String name) {

    }

    @Override
    public Account get_account(String name) {
        return null;
    }

    @Override
    public void change_pwd(String name, String pwd1, String pwd2) {

    }

    @Override
    public Account login(String name, String pwd) {
        return null;
    }

    @Override
    public void logout(Account acc) {

    }

    @Override
    public Account login(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
