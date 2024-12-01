package ru.kpfu.itis.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.dto.UserRegistrationDto;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.PasswordUtil;
import ru.kpfu.itis.util.UserNotFoundException;

public class UserService {
    private final UserDao userDao = UserDao.getInstance();

    public UserDto getUserByEmailAndPassword(String email,String password){
        User user = userDao.getUserByEmailAndPassword(email,password);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        return new UserDto(user.getName(),user.getEmail(),user.getPassword(),user.getRole());
    }

    public void auth(HttpServletRequest req, UserDto userDto){
        HttpSession session = req.getSession();
        session.setAttribute("user", userDto);
    }
    public void register(UserRegistrationDto user){
        userDao.save(new User(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                "admin"
        ));
    }
}
