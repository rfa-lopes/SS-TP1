package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Authenticator.LoggerClass;
import Authenticator.LoggerInterface;
import Models.Account;
import Models.Logger;
import Models.Operation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/loggers")
public class ServletListLoggers extends HttpServlet {

    AuthenticatorInterface aut;
    LoggerInterface logger;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
        logger = LoggerClass.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter out = resp.getWriter();
        List<Logger> logs = logger.getLoggers();

        Account acc = (Account)req.getAttribute("account");

        out.println("<h1>Loggers information: <h4>");

        out.println("<style> table, th, td { border: 1px solid black;border-collapse: collapse; } th, td { padding: 5px;text-align: left; } h1 { font-family: Arial, Helvetica, sans-serif; }</style>");

        out.println("<table style='width:100%'>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Username</th>");
        out.println("<th>Operation</th>");
        out.println("<th>Date</th>");
        out.println("</tr>");
        for(Logger l : logs) {
            out.println("<tr>");
            out.println("<td>"+l.getId()+"</td>");
            out.println("<td>"+l.getUsername()+"</td>");
            out.println("<td>"+l.getOpertation()+"</td>");
            out.println("<td>"+l.getDate()+"</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<form method='' action='/SS-TP1/home'><input type='submit' value='Home'></form>");

        logger.insertLogger(acc.getUsername(), Operation.LOGGERS);

    }

    public void destroy() {
        super.destroy();
    }

}
