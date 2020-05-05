package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Authenticator.LoggerClass;
import Authenticator.LoggerInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.EmptyInputException;
import Models.Account;
import Models.Operation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/get")
public class ServletGetAccount extends HttpServlet {

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

        Account a = (Account)req.getAttribute("account");

        String getformusername = (String)req.getAttribute("getformusername");

        try{
            Account acc = aut.get_account(getformusername);
            req.setAttribute("getusername", acc.getUsername());
            req.setAttribute("getpassword", acc.getPasswordhash());
            req.setAttribute("gettype", acc.getType());
            req.setAttribute("getisloggin", ((Boolean)acc.isIsloggedin()).toString());
            req.setAttribute("getislocked", ((Boolean)acc.isIslocked()).toString());

            rd = req.getRequestDispatcher("account.jsp");
            rd.forward(req, resp);
            logger.insertLogger(a.getUsername(), Operation.GET);
            return;
        } catch (AccountDoesNotExistsException e) {
            resp.setStatus(404);
        } catch (EmptyInputException e) {
            resp.setStatus(400);
        }
        req.setAttribute("username", a.getUsername());
        rd = req.getRequestDispatcher("home.jsp");
        rd.forward(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
