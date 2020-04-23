package Exceptions;

        import Utils.Log;

public class WeakPasswordException extends Exception {
    public WeakPasswordException(){
        super();
        Log.error("Weak password.");
    }
}
