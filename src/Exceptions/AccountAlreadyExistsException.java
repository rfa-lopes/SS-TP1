package Exceptions;

import Utils.Log;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(){
        super();
        Log.warn("Account already exists.");
    }
}
