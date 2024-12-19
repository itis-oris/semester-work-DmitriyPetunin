package ru.kpfu.itis.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;

import java.io.IOException;

@WebServlet("/delete-profile")
public class DeleteUserServlet extends HttpServlet {
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto userDto = userService.getCurrentUserDto(req);
        userService.delete(userDto);
        resp.sendRedirect("/logout");
    }
}
