package Servlets.Filters;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import Models.Account;
import Utils.Log;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName="filter1", urlPatterns = {"/create","/remove","/get","/lock","/unlock","/change","/home","/logout","/list","/loggers"})
public class AFilterAuthenticator implements Filter {

    AuthenticatorInterface aut;
    @Override
    public void init(FilterConfig filterConfig) {
        aut = AuthenticatorClass.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Log.filter("1-Authenticator filter");

        try {
            Account acc = aut.login(req, resp);
            req.setAttribute("account", acc);
            filterChain.doFilter(req, resp);
        } catch (ExpiredJwtException e) {
            resp.sendRedirect("/SS-TP1/refreshtoken");
        } catch (Exception e) {
            resp.sendRedirect("/SS-TP1/");
        }

    }

    @Override
    public void destroy() { }
}
