package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/remove")
public class ServletRemoveAccount extends HttpServlet {
    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //TODO:
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //TODO:
    }

    public void destroy() {
        super.destroy();
    }
}
