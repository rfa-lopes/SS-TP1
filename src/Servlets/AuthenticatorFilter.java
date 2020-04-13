package Servlets;

import Authenticator.AuthenticatorClass;
import Authenticator.AuthenticatorInterface;
import io.jsonwebtoken.ExpiredJwtException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/create","/remove","/get"})
public class AuthenticatorFilter implements Filter {

    AuthenticatorInterface aut;
    @Override
    public void init(FilterConfig filterConfig) {
        aut = AuthenticatorClass.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {
            aut.login(req, resp);
            filterChain.doFilter(req, resp);
        } catch (ExpiredJwtException e) {
            resp.sendRedirect("/SS-TP1/refreshtoken");
        } catch (Exception e) {
            resp.sendRedirect("/SS-TP1/home");
        }

    }

    @Override
    public void destroy() { }
}
