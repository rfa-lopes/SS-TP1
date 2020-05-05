package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Authenticator.LoggerClass;
import Authenticator.LoggerInterface;
import Models.Account;
import Models.Operation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/list")
public class ServletListAllUsers extends HttpServlet {

    AuthenticatorInterface aut;
    LoggerInterface logger;

    public void init() throws ServletException {
        super.init();
        aut = AuthenticatorClass.getInstance();
        logger = LoggerClass.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Account acc = (Account)req.getAttribute("account");

        PrintWriter out = resp.getWriter();
        List<Account> accs = aut.get_accounts();

        out.println("<h1>Accounts information: <h4>");

        out.println("<style> table, th, td { border: 1px solid black;border-collapse: collapse; } th, td { padding: 5px;text-align: left; } h1 { font-family: Arial, Helvetica, sans-serif; }</style>");

        out.println("<table style='width:100%'>");
        out.println("<tr>");
        out.println("<th>Username</th>");
        out.println("<th>Account type</th>");
        out.println("</tr>");
        for(Account a : accs) {
            out.println("<tr>");
            out.println("<td>"+a.getUsername()+"</td>");
            out.println("<td>"+a.getType()+"</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<form method='' action='/SS-TP1/home'><input type='submit' value='Home'></form>");
        logger.insertLogger(acc.getUsername(), Operation.LIST);

    }

    public void destroy() {
        super.destroy();
    }

}
