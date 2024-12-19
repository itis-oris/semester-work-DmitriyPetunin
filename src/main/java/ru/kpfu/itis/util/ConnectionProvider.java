package ru.kpfu.itis.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.exception.DbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionProvider {
    private static ConnectionProvider _instance;
    private Connection con;
    public static ConnectionProvider getInstance() throws DbException {
        if (_instance == null){
            _instance = new ConnectionProvider();
        }
        return _instance;
    }

    public Connection getCon() {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres","postgres","password");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
