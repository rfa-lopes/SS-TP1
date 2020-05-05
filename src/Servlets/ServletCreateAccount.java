package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Authenticator.LoggerClass;
import Authenticator.LoggerInterface;
import Exceptions.*;
import Models.Account;
import Models.Operation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/create")
public class ServletCreateAccount extends HttpServlet {

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

        String createusername = (String)req.getAttribute("createusername");
        String password1 = (String)req.getAttribute("createpassword1");
        String password2 = (String)req.getAttribute("createpassword2");

        try {
            aut.create_account(createusername, password1, password2);
            logger.insertLogger(acc.getUsername(), Operation.CREATE);
            resp.setStatus(201);
        } catch (PasswordDoesNotMatchException | EmptyInputException | WeakPasswordException e) {
            resp.setStatus(400);
        } catch (AccountAlreadyExistsException e) {
            resp.setStatus(422);
        }

        req.setAttribute("username", acc.getUsername());

        rd = req.getRequestDispatcher("home.jsp");
        rd.forward(req, resp);
    }

    public void destroy() {
        super.destroy();
    }

}
