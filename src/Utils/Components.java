package Utils;

import Models.Account;

public class Components {

    public static String addAccount(){

        return "<hr>" +
                "<h3>Create Account</h3>"+
                "<form method='post' action='create'>"+
                "<input type='username' placeholder='username' name='username'/>"+
                "</br>"+
                "<input type='password' placeholder='password' name='password1'/>"+
                "</br>"+
                "<input type='password' placeholder='repeat password' name='password2'/>"+
                "</br>"+
                "<input type=submit value='Confirm'>"+
                "</form>";
    }

    public static String removeAccount(){

        return "<hr>" +
                "<h3>Delete Account</h3>"+
                "<form method='post' action='remove'>"+
                "<input type='username' placeholder='username' name='username'/>"+
                "</br>"+
                "<input type=submit value='Confirm'>"+
                "</form>";
    }

    public static String logoutButton(){
        return "<hr>" +
                "<form method='POST' action='/SS-TP1/logout'>"+
                "<input type='submit' value='Logout'>"+
                "</form>";
    }

    public static String done(String message){
        return "" + message + "<hr>" +
                "<form method='GET' action='/SS-TP1/home'>"+
                "<input type='submit' value='< Home'>"+
                "</form>";
    }

    public static String getAccount(){
        return "<hr>" +
                "<h3>Get Account</h3>"+
                "<form method='post' action='get'>"+
                "<input type='username' placeholder='username' name='username'/>"+
                "</br>"+
                "<input type=submit value='Confirm'>"+
                "</form>";
    }

    public static String accountInfo(Account acc) {
        return "" +
                "Username     : "+ acc.getUsername() +
                "</br>"+
                "Password hash: "+ acc.getPasswordhash() +
                "</br>"+
                "User Type    : "+ acc.getType() +
                "</br>";
    }

    public static String changePassword(){

        return "<hr>" +
                "<h3>Change password</h3>"+
                "<form method='post' action='/SS-TP1/change'>"+
                "<input type='password' placeholder='actual password' name='password1'/>"+
                "</br>"+
                "<input type='password' placeholder='new password' name='password2'/>"+
                "</br>"+
                "<input type='password' placeholder='repeat password' name='password3'/>"+
                "</br>"+
                "<input type=submit value='Confirm'>"+
                "</form>";
    }


}
