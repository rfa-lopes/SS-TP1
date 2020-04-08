package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.LoginFailsException;
import Models.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/")
public class ServletLogin extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<HTML>");
        out.println("<HEAD></HEAD>");
        out.println("<BODY>");
        out.println("<h1>Login</h1>");
        out.println("<form method='POST' action=''>");
        out.println("<input type='username' placeholder='username' name='username'/>");
        out.println("<input type='password' placeholder='password' name='password'/>");
        out.println("<input type=submit value='Login'>");
        out.println("</BODY>");
        out.println("</HTML>");
        out.flush();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            HttpSession session = req.getSession(true);

            Account acc = aut.login(username, password);

            session.setAttribute("JWT", acc.getJwt());

            out.println("Login success");

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
