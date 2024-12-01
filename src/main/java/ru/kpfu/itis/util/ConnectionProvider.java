package ru.kpfu.itis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static ConnectionProvider _instance;
    private Connection con;
    public static ConnectionProvider getInstance() throws DbException {
        if (_instance == null){
            _instance = new ConnectionProvider();
        }
        return _instance;
    }

    private ConnectionProvider() throws DbException{
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres","postgres","password");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new DbException("Can't connect to DB.", e);
        }
    }

    public Connection getCon() {
        return con;}
}
