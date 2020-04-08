package Exceptions;

import Utils.Log;

public class PasswordDoesNotMatchException extends Exception {
    public PasswordDoesNotMatchException(){
        super();
        Log.warn("Password Does Not Match.");
    }
}
