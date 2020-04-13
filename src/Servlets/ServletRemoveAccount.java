package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.*;
import Utils.Components;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/remove")
public class ServletRemoveAccount extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");

        String message;

        try {
            aut.delete_account(username);
            message = "Account removed.";
        } catch (AccountDoesNotExistsException e) {
            message = "Account does not exists.";
        } catch (EmptyInputException e) {
            message = "Empty input.";
        }
        out.println(Components.done(message));
        out.flush();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    public void destroy() {
        super.destroy();
    }

}
