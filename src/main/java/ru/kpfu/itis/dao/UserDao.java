package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDao {

    private static UserDao instance;

    public static UserDao getInstance(){
        if (instance == null){
            return instance = new UserDao();
        }
        return instance;
    }
    private UserDao(){}

    public User getByEmailAndPassword(String email,String password) throws DbException {
        String sql = "SELECT * FROM users WHERE email=? AND password = ?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet result = ps.executeQuery();
            boolean hasOne = result.next();
            if (hasOne){
                return User.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .password(result.getString("password"))
                        .age(result.getInt("age"))
                        .build();
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get user from db",e);
        }
    }
    public User getById(Integer id){
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet result = ps.executeQuery();
            boolean hasOne = result.next();
            if (hasOne){
                return User.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .email(result.getString("email"))
                        .password(result.getString("password"))
                        .age(result.getInt("age"))
                        .build();
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("user with such id does not exist ");
        }
    }
    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password, age) values(?,?,?,?)";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getAge());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException("Can't save user :(",e);
        }
    }

    public String getPasswordByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email=?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,email);
            ResultSet resultSet = ps.executeQuery();
            boolean hasOne = resultSet.next();
            if (hasOne){
                return resultSet.getString("password");
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't ger password with email: " + email,e);
        }
    }
}
