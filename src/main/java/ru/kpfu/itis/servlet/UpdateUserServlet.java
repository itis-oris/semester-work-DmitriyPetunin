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

import java.io.IOException;

@WebServlet("/edit-profile")
public class UpdateUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRegistrationDto userRegistrationDto = userService.getCurrentUserRegistrationDto(req);
        req.setAttribute("user",userRegistrationDto);
        req.getRequestDispatcher("/WEB-INF/view/users/editprofile.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String newRepeatPassword = req.getParameter("newRepeatPassword");

        UserRegistrationDto user = userService.getCurrentUserRegistrationDto(req);

        if (PasswordUtil.encrypt(oldPassword).equals(user.getPassword())){
            if (newPassword.equals(newRepeatPassword)){
                Integer id = userService.getUserByEmailAndPassword(user.getEmail(),user.getPassword()).getId();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(PasswordUtil.encrypt(newPassword));
                boolean isUpdated = userService.updateInformation(user,id);
                if (isUpdated){
                    userService.auth(req, UserDto.builder()
                                    .id(userService.getCurrentUserDto(req).getId())
                                    .name(user.getName())
                                    .dateOfBirth(user.getDateOfBirth())
                                    .email(user.getEmail())
                            .build());
                    req.getRequestDispatcher("/WEB-INF/view/users/profile.jsp").forward(req,resp);
                } else {
                    req.setAttribute("error", "Не удалось обновить данные пользователя");
                    req.getRequestDispatcher("/WEB-INF/view/users/editprofile.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error","пароли не совпадают");
                req.getRequestDispatcher("/WEB-INF/view/users/editprofile.jsp").forward(req,resp);
            }
        } else {
            req.setAttribute("error","неверно введён пароль");
            req.getRequestDispatcher("/WEB-INF/view/users/editprofile.jsp").forward(req,resp);
        }
    }
}
