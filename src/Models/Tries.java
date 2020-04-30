package Models;

import Config.Configs;
import Exceptions.ExceedNrTriesException;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class Tries {

    private int nrTries;
    private Date lastTry;

    public Tries() {
        this.nrTries = 0;
        this.lastTry = null;
    }

    public void addTry() throws ExceedNrTriesException {

        if(lastTry != null){
            long diffMillis = new Date().getTime() -  lastTry.getTime();
            long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis);

            if(diffMinutes >= Configs.TRIES_TIME)
                nrTries = 0;
        }
        lastTry = new Date();

        if(nrTries >= 2)
            throw new ExceedNrTriesException();

        nrTries ++;
    }
}
