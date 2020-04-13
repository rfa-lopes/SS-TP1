package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Models.Account;
import Utils.Cookies;
import Utils.JwtUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

import static Utils.JwtUtil.*;

@WebServlet(urlPatterns = "/refreshtoken")
public class ServletRefreshToken extends HttpServlet {
    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LinkedList<String> tokens = Cookies.getCookiesInString(req, REFRESH_TOKEN_TYPE);
        try {
            String refreshtoken = tokens.get(0);

            String username = JwtUtil.parseJWT(refreshtoken);

            Account acc = aut.get_account(username);

            Cookie authenticatorToken = acc.getToken();

            resp.addCookie(authenticatorToken);

            resp.sendRedirect("home");

        } catch (Exception e){
            resp.sendRedirect("/SS-TP1/");
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        doGet(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
