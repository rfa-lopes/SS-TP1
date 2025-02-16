package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Authenticator.LoggerClass;
import Authenticator.LoggerInterface;
import Exceptions.*;
import Models.Account;
import Models.Logger;
import Models.Operation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/change")
public class ServletChangePassword extends HttpServlet {
    AuthenticatorInterface aut;
    LoggerInterface logger;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
        logger = LoggerClass.getInstance();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        RequestDispatcher rd;

        Account acc = (Account)req.getAttribute("account");

        String username = acc.getUsername();

        String password = (String)req.getAttribute("changepassword1");
        String new1 = (String)req.getAttribute("changepassword2");
        String new2 = (String) req.getAttribute("changepassword3");

        try {
            aut.checkPassword(username, password);
            aut.change_pwd(username, new1, new2);
            logger.insertLogger(acc.getUsername(), Operation.CHANGE);
            resp.setStatus(201);
        } catch (EmptyInputException | PasswordDoesNotMatchException | WeakPasswordException e) {
            resp.setStatus(400);
        } catch (LoginFailsException | AccountDoesNotExistsException e) {
            resp.setStatus(401);
        }

        req.setAttribute("username", username);
        rd = req.getRequestDispatcher("home.jsp");
        rd.forward(req, resp);
    }

    public void destroy() {
        super.destroy();
    }

}
