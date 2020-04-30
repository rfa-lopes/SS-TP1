package Exceptions;

        import Utils.Log;

public class ExceedNrTriesException extends Exception {
    public ExceedNrTriesException(){
        super();
        Log.error("Exceed tryes");
    }
}
