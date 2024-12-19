package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.UserRegistrationDto;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.util.PasswordUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        String dateOfBirth = req.getParameter("date_of_birth");
        System.out.println(dateOfBirth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (!email.trim().isEmpty() && !password.trim().isEmpty() &&
                    !name.isEmpty() && !dateOfBirth.isEmpty()) {
                UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                        .name(name)
                        .email(email)
                        .password(password)
                        .dateOfBirth(dateFormat.parse(dateOfBirth))
                        .build();
                userService.register(userRegistrationDto);
                userService.auth(req, userService.getUserByEmailAndPassword(email, password));

                LOGGER.info("Registration success");
                resp.sendRedirect("/profile");
            } else {
                resp.sendRedirect("/register");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
