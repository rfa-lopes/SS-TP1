package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.EmptyInputException;
import Exceptions.IsAdminException;
import Models.Account;
import Models.UserType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/lock")
public class ServletLockAccount extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        resp.setContentType("text/html");
        RequestDispatcher rd;

        Account acc = (Account)req.getAttribute("account");

        try {
            String lockusername = (String)req.getAttribute("lockformusername");
            aut.lock(lockusername);
            resp.setStatus(201);
        } catch (AccountDoesNotExistsException e) {
            resp.setStatus(404);
        } catch (EmptyInputException e) {
            resp.setStatus(400);
        } catch (IsAdminException e) {
            resp.setStatus(500);
        }
        req.setAttribute("username", acc.getUsername());
        rd = req.getRequestDispatcher("home.jsp");
        rd.forward(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
