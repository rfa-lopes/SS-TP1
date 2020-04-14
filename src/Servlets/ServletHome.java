package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Models.Account;
import Models.UserType;
import Utils.Components;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/home")
public class ServletHome extends HttpServlet {

    AuthenticatorInterface aut;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<HTML><HEAD></HEAD><BODY>");

        try {
            Account acc = aut.login(req, resp);
            out.println("<h1>Hello "+acc.getUsername()+"</h1>");

            if(acc.getType().equals(UserType.ADMIN.name())){
                out.println(Components.addAccount());
                out.println(Components.removeAccount());
                out.println(Components.getAccount());
            }

            out.println(Components.changePassword());


            out.println(Components.logoutButton());

        } catch (ExpiredJwtException e) {
            resp.sendRedirect("/SS-TP1/refreshtoken");
        } catch (Exception e) {
            resp.sendRedirect("/SS-TP1/");
        }

        out.println("</BODY></HTML>");
        out.flush();

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    public void destroy() {
        super.destroy();
    }
}
