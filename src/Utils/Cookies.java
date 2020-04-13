package Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class Cookies {

    public static LinkedList<String> getCookiesInString(HttpServletRequest req, String ... cookiesName) {
        Cookie[] cookies = req.getCookies();

        LinkedList<String> cookiesValues = new LinkedList<>();

        for (Cookie cookie : cookies)
            for (String name : cookiesName)
                if(cookie.getName().equals(name))
                    cookiesValues.add(cookie.getValue());

        return cookiesValues;
    }
}
