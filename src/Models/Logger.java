package Models;

public class Logger {

    String id;
    String username;
    String opertation;
    String date;

    public Logger(String id, String username, String opertation, String date) {
        this.id = id;
        this.username = username;
        this.opertation = opertation;
        this.date = date;
    }

    public Logger() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpertation() {
        return opertation;
    }

    public void setOpertation(String opertation) {
        this.opertation = opertation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
