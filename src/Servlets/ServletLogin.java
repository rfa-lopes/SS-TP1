package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Authenticator.LoggerClass;
import Authenticator.LoggerInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.EmptyInputException;
import Exceptions.ExceedNrTriesException;
import Exceptions.LoginFailsException;
import Models.Account;
import Models.Operation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/", "/login"})
public class ServletLogin extends HttpServlet {

    AuthenticatorInterface aut;
    LoggerInterface logger;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
        logger = LoggerClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        RequestDispatcher rd;
        try {
            Account acc = aut.login(req,resp);
            req.setAttribute("username", acc.getUsername());
            rd = req.getRequestDispatcher("home.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {}

        resp.setStatus(200);
        rd = req.getRequestDispatcher("login.jsp");
        rd.forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        RequestDispatcher rd;

        try {
            String username = (String) req.getAttribute("username");
            String password = (String) req.getAttribute("password");

            String ip = req.getRemoteAddr();

            Account acc = aut.login(ip, username, password);

            Cookie authenticatorToken = acc.getToken();
            Cookie refreshToken = acc.getRefreshToken();

            resp.addCookie(authenticatorToken);
            resp.addCookie(refreshToken);

            req.setAttribute("username", acc.getUsername());
            rd = req.getRequestDispatcher("home.jsp");
            rd.forward(req, resp);
            logger.insertLogger(acc.getUsername(), Operation.LOGIN);
            return;

        } catch (LoginFailsException e) {
            resp.setStatus(401);
        } catch (AccountDoesNotExistsException e) {
            resp.setStatus(404);
        } catch (EmptyInputException e) {
            resp.setStatus(400);
        } catch (ExceedNrTriesException e) {
            resp.setStatus(402);
        }

        rd = req.getRequestDispatcher("login.jsp");
        rd.forward(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
