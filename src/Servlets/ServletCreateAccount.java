package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.*;
import Utils.Components;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/create")
public class ServletCreateAccount extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");

        String message;

        try {
            aut.create_account(username, password1, password2);
            message = "Account created.";
        } catch (PasswordDoesNotMatchException e) {
            message = "Password does not match.";
        } catch (AccountAlreadyExistsException e) {
            message = "Account Already Exists.";
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
