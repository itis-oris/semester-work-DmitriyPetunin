package ru.kpfu.itis.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
@WebFilter(urlPatterns = "/*")
public class AuthenticationFilter extends HttpFilter {

    private final String[] authPages = {"/profile","/create-travel","/delete-profile","/edit-profile","/logout","/travel/edit"};


    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") == null && isAuthPages(uri)) {
            res.sendRedirect("/sign-in");
        } else {
            chain.doFilter(req, res);
        }
    }

    public boolean isAuthPages(String uri){
        for (String pages :authPages){
            if (pages.equals(uri)){
                return true;
            }
        }
        return false;
    }

}
