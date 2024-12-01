package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserDao {
    private ConnectionProvider connectionProvider = ConnectionProvider.getInstance();

    private static UserDao instance;

    public static UserDao getInstance(){
        if (instance == null){
            return new UserDao();
        }
        return instance;
    }
    private UserDao(){}

    public User getUserByEmailAndPassword(String email,String password) throws DbException {
        try {
            PreparedStatement pt = connectionProvider.getCon()
                    .prepareStatement("SELECT * FROM users WHERE email=? AND password = ?");
            pt.setString(1,email);
            pt.setString(2,password);
            ResultSet result = pt.executeQuery();
            boolean hasOne = result.next();
            if (hasOne){
                return new User(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getString("password"),
                        result.getString("role")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get user from db",e);
        }
    }
    public void save(User user) {
        String sql = "INSERT INTO users (name, email, password, role) values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connectionProvider.getCon().prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getRole());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
