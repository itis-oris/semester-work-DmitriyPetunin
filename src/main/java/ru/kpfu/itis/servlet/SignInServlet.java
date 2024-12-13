package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.util.PasswordUtil;
import ru.kpfu.itis.util.UserNotFoundException;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "signInServlet",urlPatterns = "/signin")
public class SignInServlet extends HttpServlet {

    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger(SignInServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/security/signin.jsp").forward(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputEmail = req.getParameter("email");
        String inputPassword = PasswordUtil.encrypt(req.getParameter("password"));

        if (!inputEmail.trim().isEmpty() && !inputPassword.trim().isEmpty()){
            try {
                String password = userService.getPasswordByEmail(inputEmail);
                if (password.equals(inputPassword)){
                    UserDto userDto = userService.getUserByEmailAndPassword(inputEmail,inputPassword);
                    userService.auth(req,userDto);

                    LOGGER.info("auth success");
                    resp.sendRedirect("/profile");
                } else {
                    req.setAttribute("error","Введён невереный пароль");
                    req.getRequestDispatcher("/WEB-INF/view/security/signin.jsp").forward(req,resp);
                }
            } catch (UserNotFoundException e){
                throw new ServletException(e);
            }
        } else {
            LOGGER.warning("Authentication failed for user: " + inputEmail);
            req.setAttribute("error","Введите оставшиеся поля");
            req.getRequestDispatcher("/WEB-INF/view/security/signin.jsp").forward(req,resp);
        }
    }
}
