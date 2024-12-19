package ru.kpfu.itis.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.dto.UserRegistrationDto;
import ru.kpfu.itis.dto.UserTravelDto;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.exception.UserNotFoundException;
import ru.kpfu.itis.util.PasswordUtil;
import ru.kpfu.itis.util.UserUtil;

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
        if (id != null){
            User user = userDao.getById(id);
            if (user != null){
                return UserTravelDto.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .age(UserUtil.calculateAge(user.getDateOfBirth()))
                        .build();
            } else return null;
        } else {
            return null;
        }
    }

    public UserDto getUserByEmailAndPassword(String email,String password){
        User user = userDao.getByEmailAndPassword(email,password);
        if (user == null){
            throw new UserNotFoundException("User not found");
        }
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }

    public void auth(HttpServletRequest req, UserDto userDto){
        HttpSession session = req.getSession();
        session.setAttribute("user", userDto);
    }
    public void register(UserRegistrationDto user){
        userDao.save(User.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .dateOfBirth(user.getDateOfBirth())
                        .build());
    }
    public String getPasswordByEmail(String email){
        return userDao.getPasswordByEmail(email);
    }
    public UserDto getCurrentUserDto(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserDto currentUser = (UserDto) session.getAttribute("user");

        if (currentUser == null) {
            return null;
        }

        return currentUser;
    }
    public UserTravelDto getCurrentUserTravelDto(HttpServletRequest req) {
        UserDto currentUser = getCurrentUserDto(req);
        return UserTravelDto.builder()
                .id(currentUser.getId())
                .name(currentUser.getName())
                .age(UserUtil.calculateAge(currentUser.getDateOfBirth()))
                .build();
    }
    public UserRegistrationDto getCurrentUserRegistrationDto(HttpServletRequest req) {
        Integer id = getCurrentUserDto(req).getId();
        User user = userDao.getById(id);
        return UserRegistrationDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }
    public void delete(UserDto userDto){
        userDao.delete(User.builder()
                .id(userDto.getId())
                .build());
    }
    public boolean updateInformation(UserRegistrationDto user,Integer id){
        return userDao.updateInformation(User.builder()
                .id(id)
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .build());
    }
}
