package Exceptions;

import Utils.Log;

public class AccountDoesNotExistsException extends Exception{
    public AccountDoesNotExistsException(String username){
        super();
        Log.error("Account does not exist: " + username);
    }
}
