package Servlets.Filters;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Models.Account;
import Utils.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebFilter(filterName="filter3", urlPatterns = {"/login","/create","/remove","/get","/change","/home","/lock","/unlock"})
public class CFilterInvalidChars implements Filter{

    private static final int MAX_PARAMETERS = 5;
    AuthenticatorInterface aut;
    @Override
    public void init(FilterConfig filterConfig) {
        aut = AuthenticatorClass.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Log.filter("3-User input filter");

        if(req.getParameterMap().size() > MAX_PARAMETERS) {
            resp.sendRedirect("/SS-TP1/");
            return;
        }

        Enumeration enumeration = req.getParameterNames();
        while(enumeration.hasMoreElements()){
            String parameterName = (String) enumeration.nextElement();
            String value = req.getParameter(parameterName);

            //Não temos parametros vazios
            if(value.equals("")){
                RequestDispatcher rd;
                resp.setStatus(401);
                rd = req.getRequestDispatcher("home.jsp");
                rd.forward(req, resp);
                return;
            }

            //Se não for uma password
            if(!parameterName.contains("password"))
                value = value.replaceAll("[^A-Za-z0-9]", "?");

            req.setAttribute(parameterName, value);
        }
        Account acc = (Account)req.getAttribute("account");
        if(acc != null)
            req.setAttribute("account", acc);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }

}
