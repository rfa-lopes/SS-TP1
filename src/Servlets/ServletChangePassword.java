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

@WebServlet(urlPatterns = "/change")
public class ServletChangePassword extends HttpServlet {
    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
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
            aut.login(username, password);
            aut.change_pwd(username, new1, new2);
            resp.setStatus(201);
        } catch (EmptyInputException | PasswordDoesNotMatchException e) {
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
