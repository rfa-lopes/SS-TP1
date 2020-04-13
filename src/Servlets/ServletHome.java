package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.LoginFailsException;
import Models.Account;
import Utils.Log;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/home")
public class ServletHome extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {
            Account acc = aut.login(req, resp);
            out.println("<h1>HELLO "+acc.getUsername()+"</h1>");
            //TODO:
        } catch (LoginFailsException e) {
            resp.sendRedirect("/SS-TP1/");
            Log.error("Try access /home without authentication.");
        } catch (SignatureException e) {
            resp.sendRedirect("/SS-TP1/");
            Log.error("JWT invalid signature.");
        } catch (ExpiredJwtException e) {
            resp.sendRedirect("/SS-TP1/");
            Log.error("JWT expired.");
        }
        out.flush();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
