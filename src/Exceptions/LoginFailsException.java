package Exceptions;

import Utils.Log;

public class LoginFailsException extends Exception {
    public LoginFailsException(){
        super();
        Log.warn("Login fail.");
    }
}
