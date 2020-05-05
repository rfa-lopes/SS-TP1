package Servlets.Filters;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Models.Account;
import Models.UserType;
import Utils.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="filter2", urlPatterns = {"/create","/remove","/get","/lock","/unlock","/list","/loggers"})
public class BFilterRolesTypes implements Filter{

    AuthenticatorInterface aut;

    @Override
    public void init(FilterConfig filterConfig) {
        aut = AuthenticatorClass.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Log.filter("2-Role filter");

        Account acc = (Account)req.getAttribute("account");
        req.setAttribute("account", acc);

        if(acc.getType().equalsIgnoreCase(UserType.ACCOUNT.name())){
            RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
            rd.forward(req, resp);
        }
        else
        if(acc.getType().equalsIgnoreCase(UserType.ADMIN.name())){
            req.setAttribute("account", acc);
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() { }

}
