package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {
    private static ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    private static TravelDao instance;

    public static TravelDao getInstance()  {
        if (instance == null){
            return instance = new TravelDao();
        }
        return instance;
    }

    private TravelDao() {}

    public int getCount() throws DbException{
        try {
            Statement st = connectionProvider.getCon().createStatement();
            ResultSet resultSet = st.executeQuery("SELECT COUNT(id) AS cnt FROM travels");
            resultSet.next();
            return resultSet.getInt("cnt");
        } catch (SQLException e) {
            throw new DbException("Can't get count of travel in Db",e);
        }
    }
    public List<Travel> getAllTravels() throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travels");
            ResultSet result = st.executeQuery();
            List<Travel> travels = new ArrayList<>();
            while (result.next()){
                Travel travel = new Travel(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("duration"),
                        result.getString("author")

                );
                travels.add(travel);
            }
            return travels;
        } catch (SQLException e) {
            throw new DbException("Can't get travel list from db", e);
        }
    }

    public Travel getDetail(int id) throws DbException {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM travels WHERE id = ?");
            st.setInt(1,id);
            ResultSet result = st.executeQuery();
            boolean hasOne = result.next();
            if (hasOne){
                return new Travel(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("duration"),
                        result.getString("author")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get travel list from db", e);
        }
    }
}
