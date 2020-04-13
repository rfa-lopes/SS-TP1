package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Exceptions.AccountDoesNotExistsException;
import Exceptions.LoginFailsException;
import Models.Account;
import Utils.Log;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/")
public class ServletLogin extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try{
            aut.login(req, resp);
            resp.sendRedirect("home");
        } catch (Exception e) {
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
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        //Para evitar dois loggins do mesmo cliente
        try{
            aut.login(req, resp);
            resp.sendRedirect("home");
        } catch (Exception h) {

            try {
                String username = req.getParameter("username");
                String password = req.getParameter("password");

                //HttpSession session = req.getSession(true);

                Account acc = aut.login(username, password);

                Cookie autToken = new Cookie("Authorization", acc.getJwt());
                autToken.setHttpOnly(true); //XSS mitigation
                autToken.setSecure(false); //http=false ; https=true

                resp.addCookie(autToken);

                //session.setAttribute("JWT", acc.getJwt());

                resp.sendRedirect("home");

            } catch (AccountDoesNotExistsException e) {
                doGet(req, resp);
                out.println("Login fails");
            } catch (LoginFailsException e) {
                doGet(req, resp);
                out.println("Login fails");
            } catch (SignatureException e) {
                doGet(req, resp);
                Log.error("JWT invalid signature.");
            } catch (ExpiredJwtException e) {
                doGet(req, resp);
                Log.error("JWT expired.");
            }
            out.flush();
        }
    }

    public void destroy() {
        super.destroy();
    }
}
