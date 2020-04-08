package Servlets;


import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.LoginFailsException;
import Utils.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/test")
public class TestingServlet extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("Hello world");
        Log.info("Test logger");
        Log.warn("Test logger");
        Log.error("Test logger");
        out.flush();
    }
}

