package Exceptions;

import Utils.Log;

public class LoginFailsException extends Exception {
    public LoginFailsException(String username){
        super();
        Log.error("Login fails by: " + username);
    }

    public LoginFailsException( ){
        super();
        Log.error("Login fails.");
    }
}
