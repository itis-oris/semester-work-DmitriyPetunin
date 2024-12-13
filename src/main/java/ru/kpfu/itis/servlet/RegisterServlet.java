package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.dto.UserRegistrationDto;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.util.PasswordUtil;
import ru.kpfu.itis.util.UserNotFoundException;

import java.io.IOException;
import java.util.SortedMap;
import java.util.logging.Logger;

@WebServlet(name = "registerServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/security/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = PasswordUtil.encrypt(req.getParameter("password"));
        String name = req.getParameter("name");
        String age = req.getParameter("age");

        if (!email.trim().isEmpty() && !password.trim().isEmpty() &&
                !name.isEmpty() && !age.isEmpty()) {
            UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .age(Integer.parseInt(age))
                    .build();
            userService.register(userRegistrationDto);
            userService.auth(req,userRegistrationDto);
            
            LOGGER.info("Registration success");
            resp.sendRedirect("/profile");
        } else {
            resp.sendRedirect("/register");
            //req.getRequestDispatcher("/WEB-INF/view/security/register.jsp").forward(req,resp);
        }
    }
}
