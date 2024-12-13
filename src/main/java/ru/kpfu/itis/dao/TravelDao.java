package ru.kpfu.itis.dao;

import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.dto.UserTravelDto;
import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.service.UserService;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelDao {
    private static TravelDao instance;
    private final UserDao userDao = UserDao.getInstance();

    public static TravelDao getInstance()  {
        if (instance == null){
            return instance = new TravelDao();
        }
        return instance;
    }

    private TravelDao() {}

    public int getCount() throws DbException{ // норм
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT COUNT(id) AS cnt FROM travels");
            resultSet.next();
            return resultSet.getInt("cnt");
        } catch (SQLException e) {
            throw new DbException("Can't get count of travel in Db",e);
        }
    }
    public List<Travel> getAllTravels() throws DbException { // норм
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement st = connection.prepareStatement("SELECT * FROM travels");
            ResultSet result = st.executeQuery();
            List<Travel> travels = new ArrayList<>();
            while (result.next()){
                Travel travel = Travel.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .description(result.getString("description"))
                        .duration(result.getString("duration"))
                        .authorId(userDao.getById(result.getInt("author_id")).getId())
                        .build();
                travels.add(travel);
            }
            return travels;
        } catch (SQLException e) {
            throw new DbException("Can't get travel list from db", e);
        }
    }

    public Travel getById(int travelId) throws DbException { // норм
        String sql = "SELECT * FROM travels WHERE id = ?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1,travelId);
            ResultSet result = st.executeQuery();
            boolean hasOne = result.next();
            User user = userDao.getById(result.getInt("author_id"));
            if (hasOne){
                return Travel.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .description(result.getString("description"))
                        .duration(result.getString("duration"))
                        .authorId(user.getId())
                        .build();
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get travel list from db", e);
        }
    }
    public Integer save(Travel travel) throws DbException{ // норм
        Integer travel_id = null;
        String sql = "INSERT INTO travels (name,description,duration,author_id) VALUES(?,?,?,?) RETURNING id";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,travel.getName());
            ps.setString(2,travel.getDescription());
            ps.setString(3,travel.getDuration());
            ps.setInt(4,travel.getAuthorId());
            ResultSet result = ps.executeQuery();

            if (result.next()){
                travel_id = result.getInt("id");
            }
            return travel_id; // вернёт id путешествия по которму надо сделать insertЫ в images
        } catch (SQLException e){
            throw new DbException("Can't save travel into db", e);
        }
    }
}
