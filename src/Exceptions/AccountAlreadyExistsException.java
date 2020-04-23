package Exceptions;

import Utils.Log;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(){
        super();
        Log.error("Account already exists.");
    }
}
