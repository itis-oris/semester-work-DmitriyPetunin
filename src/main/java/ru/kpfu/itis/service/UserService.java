package ru.kpfu.itis.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.dto.UserRegistrationDto;
import ru.kpfu.itis.dto.UserTravelDto;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.PasswordUtil;
import ru.kpfu.itis.util.UserNotFoundException;

public class UserService {
    private final UserDao userDao = UserDao.getInstance();
    public UserDto getById(Integer id){
        User user = userDao.getById(id);
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
    public UserTravelDto getUserTravelDtoById(Integer id){
        User user = userDao.getById(id);
        return UserTravelDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .build();
    }

    public UserDto getUserByEmailAndPassword(String email,String password){
        User user = userDao.getByEmailAndPassword(email,password);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        return new UserDto(user.getId(),user.getName(),user.getEmail(),user.getAge());
    }

    public void auth(HttpServletRequest req, UserDto userDto){
        HttpSession session = req.getSession();
        session.setAttribute("user", userDto);
    }
    public void auth(HttpServletRequest req, UserRegistrationDto userDto){
        HttpSession session = req.getSession();
        session.setAttribute("user", userDto);
    }
    public void register(UserRegistrationDto user){
        userDao.save(User.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .age(user.getAge())
                        .build());
    }
    public String getPasswordByEmail(String email){
        return userDao.getPasswordByEmail(email);
    }
    public UserDto getCurrentUserDto(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto currentUser = (UserDto) session.getAttribute("user");

        if (currentUser == null) {
            throw new RuntimeException("Пользователь не аутентифицирован");
        }

        return currentUser;
    }
    public UserTravelDto getCurrentUserTravelDto(HttpServletRequest req) {
        UserDto currentUser = getCurrentUserDto(req);
        return UserTravelDto.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .age(currentUser.getAge())
                .build();
    }
}
