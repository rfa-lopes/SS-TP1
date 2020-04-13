package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.EmptyInputException;
import Models.Account;
import Utils.Components;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/get")
public class ServletGetAccount extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<HTML><HEAD></HEAD><BODY>");

        String username = req.getParameter("username");

        String message;

        try{
            Account acc = aut.get_account(username);
            message = Components.accountInfo(acc);
        } catch (AccountDoesNotExistsException e) {
            message = "Account does not exists.";
        } catch (EmptyInputException e) {
            message = "Empty input.";
        }

        out.println(Components.done(message));
        out.flush();

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
