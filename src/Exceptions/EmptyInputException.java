package Exceptions;

import Utils.Log;

public class EmptyInputException extends Exception {
    public EmptyInputException(){
        super();
        Log.warn("Empty input.");
    }
}
