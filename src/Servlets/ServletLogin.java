package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.EmptyInputException;
import Exceptions.LoginFailsException;
import Models.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/", "/login"})
public class ServletLogin extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        resp.setStatus(200);
        RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
        rd.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        RequestDispatcher rd;

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Account acc = aut.login(username, password);

            Cookie authenticatorToken = acc.getToken();
            Cookie refreshToken = acc.getRefreshToken();

            resp.addCookie(authenticatorToken);
            resp.addCookie(refreshToken);

            //resp.sendRedirect("home");

            req.setAttribute("username", acc.getUsername());

            rd = req.getRequestDispatcher("home.jsp");
            rd.forward(req, resp);
            return;

        } catch (LoginFailsException e) {
            resp.setStatus(401);
        } catch (AccountDoesNotExistsException e) {
            resp.setStatus(404);
        } catch (EmptyInputException e) {
            resp.setStatus(400);
        }
        rd = req.getRequestDispatcher("login.jsp");
        rd.forward(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
