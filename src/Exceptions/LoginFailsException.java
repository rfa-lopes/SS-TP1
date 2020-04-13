package Exceptions;

import Utils.Log;

public class LoginFailsException extends Exception {
    public LoginFailsException(String username){
        super();
        Log.warn("Login fails by: " + username);
    }

    public LoginFailsException( ){
        super();
        Log.warn("Login fails.");
    }
}
