package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Models.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class ServletLogout extends HttpServlet {
    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        try {
            Account acc = aut.login(req, resp);
            aut.logout(acc);
        } catch (Exception e){ }

        resp.sendRedirect("/SS-TP1/login");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
