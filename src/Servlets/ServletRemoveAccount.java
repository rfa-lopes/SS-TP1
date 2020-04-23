package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.*;
import Models.Account;

import javax.servlet.RequestDispatcher;
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

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        RequestDispatcher rd;

        Account acc = (Account)req.getAttribute("account");

        String deleteusername = (String)req.getAttribute("deleteusername");

        try {
            aut.delete_account(deleteusername);
            resp.setStatus(201);
        } catch (AccountDoesNotExistsException e) {
            resp.setStatus(404);
        } catch (EmptyInputException e) {
            resp.setStatus(400);
        }

        req.setAttribute("username", acc.getUsername());
        rd = req.getRequestDispatcher("home.jsp");
        rd.forward(req, resp);
    }

    public void destroy() {
        super.destroy();
    }

}
