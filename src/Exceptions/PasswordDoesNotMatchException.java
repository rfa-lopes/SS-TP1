package Exceptions;

import Utils.Log;

public class PasswordDoesNotMatchException extends Exception {
    public PasswordDoesNotMatchException(){
        super();
        Log.error("Password Does Not Match.");
    }
}
