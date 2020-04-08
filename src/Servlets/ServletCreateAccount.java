package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountAlreadyExistsException;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.LoginFailsException;
import Exceptions.PasswordDoesNotMatchException;
import Models.Account;

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

        try {
            aut.login(req, resp);
            aut.create_account(username, password1, password2);
            out.println("<h3>Account created.</h3>");
            out.println("<form method='get' action='/SS-TP1'>");
            out.println("<input type='submit' value='Login'>");
            out.println("</form>");
            out.flush();
        } catch (PasswordDoesNotMatchException e) {
            out.println("Password does not match.");
        } catch (AccountAlreadyExistsException e) {
            out.println("Account Already Exists.");
        } catch (LoginFailsException e) {
            out.println("Login fails");
        } catch (AccountDoesNotExistsException e) {
            out.println("Login fails");
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {
            aut.login(req, resp);
            out.println("<h3>Create Account</h3>");
            out.println("<form method='post' action='create'>");
            out.println("<input type='username' placeholder='username' name='username'/>");
            out.println("</br>");
            out.println("<input type='password' placeholder='password' name='password1'/>");
            out.println("</br>");
            out.println("<input type='password' placeholder='repeat password' name='password2'/>");
            out.println("</br>");
            out.println("<input type=submit value='Confirm'>");
            out.println("</form>");

            out.println("<form method='get' action='/SS-TP1'>");
            out.println("<input type='submit' value='Go main'>");
            out.println("</form>");
        } catch (AccountDoesNotExistsException e) {
            out.println("Login fails");
        } catch (LoginFailsException e) {
            out.println("Login fails");
        }
        out.flush();
    }

    public void destroy() {
        super.destroy();
    }

}
