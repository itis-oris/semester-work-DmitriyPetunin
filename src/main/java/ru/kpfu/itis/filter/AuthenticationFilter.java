//package ru.kpfu.itis.filter;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//
//import java.io.IOException;
//@WebFilter(urlPatterns = "/*")
//public class AuthenticationFilter implements Filter {
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        String uri = httpServletRequest.getRequestURI();
//        HttpSession session = httpServletRequest.getSession(false);
//        if (session == null && !uri.contains("login")) {
//            ((HttpServletResponse) response).sendRedirect("/signin");
//        } else {
//            chain.doFilter(request, response);
//        }
//    }
//}
