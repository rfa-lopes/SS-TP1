package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.*;
import Models.Account;
import Utils.Components;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/change")
public class ServletChangePassword extends HttpServlet {
    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String password3 = req.getParameter("password3");

        String message;

        try {
            Account acc = aut.login(req, resp);
            String username = acc.getUsername();
            aut.login(username,password1);
            aut.change_pwd(acc.getUsername(),password2,password3);
            message = "Password changed.";
        } catch (EmptyInputException e) {
            message = "Empty input.";
        } catch (LoginFailsException e) {
            message = "Wrong password.";
            e.printStackTrace();
        } catch (AccountDoesNotExistsException e) {
            message = "Empty input.";
            e.printStackTrace();
        } catch (PasswordDoesNotMatchException e) {
            message = "Password does not match.";
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
