package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Models.Account;

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

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<HTML><HEAD></HEAD><BODY>");

        out.println("<h1>Login</h1>");
        out.println("<form method='POST' action=''>");
        out.println("<input type='username' placeholder='username' name='username'/>");
        out.println("<input type='password' placeholder='password' name='password'/>");
        out.println("<input type=submit value='Login'>");
        out.println("</form>");

        if(resp.getStatus() == 401) {
            out.println("<hr>");
            out.println("Not authorized.");
        }

        out.println("</BODY></HTML>");
        out.flush();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            Account acc = aut.login(username, password);

            Cookie authenticatorToken = acc.getToken();
            Cookie refreshToken = acc.getRefreshToken();

            resp.addCookie(authenticatorToken);
            resp.addCookie(refreshToken);

            resp.sendRedirect("home");

        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            doGet(req, resp);
            out.println("Not authorized.");
            out.flush();
        }
    }

    public void destroy() {
        super.destroy();
    }
}
