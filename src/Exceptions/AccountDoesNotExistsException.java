package Exceptions;

import Utils.Log;

public class AccountDoesNotExistsException extends Exception{
    public AccountDoesNotExistsException(){
        super();
        Log.warn("Account does not exists.");
    }
}
