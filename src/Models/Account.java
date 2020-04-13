package Models;

import Utils.JwtUtil;

import javax.servlet.http.Cookie;

import static Utils.JwtUtil.JWT_TYPE;
import static Utils.JwtUtil.REFRESH_TOKEN_TYPE;

public class Account {

    String username;
    String passwordhash;
    boolean isloggedin;
    boolean islocked;
    String type;

    public Account() { }

    public Cookie getToken() {
        Cookie token = new Cookie(JWT_TYPE, JwtUtil.createJWT(username));
        token.setHttpOnly(true); //XSS mitigation
        token.setSecure(false); //http=false ; https=true
        return token;
    }

    public Cookie getRefreshToken() {
        Cookie token = new Cookie(REFRESH_TOKEN_TYPE, JwtUtil.createJWTRefreshToken(username));
        token.setHttpOnly(true); //XSS mitigation
        token.setSecure(false); //http=false ; https=true
        token.setPath("/SS-TP1/refreshtoken");
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public boolean isIsloggedin() {
        return isloggedin;
    }

    public void setIsloggedin(boolean isloggedin) {
        this.isloggedin = isloggedin;
    }

    public boolean isIslocked() {
        return islocked;
    }

    public void setIslocked(boolean islocked) {
        this.islocked = islocked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
