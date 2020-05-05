package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Authenticator.LoggerClass;
import Authenticator.LoggerInterface;
import Models.Account;
import Models.Operation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class ServletLogout extends HttpServlet {
    AuthenticatorInterface aut;
    LoggerInterface logger;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
        logger = LoggerClass.getInstance();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        Account acc = (Account)req.getAttribute("account");
        try {
            aut.logout(acc);
            logger.insertLogger(acc.getUsername(), Operation.LOGOUT);
        } catch (Exception e){ }

        resp.sendRedirect("/SS-TP1");
    }

    public void destroy() {
        super.destroy();
    }
}
