package Exceptions;

import Utils.Log;

public class IsAdminException extends Exception {
    public IsAdminException(){
        super();
        Log.error("Cannot lock admin account.");
    }
}
