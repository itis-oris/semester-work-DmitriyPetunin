package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.exception.DbException;

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

    public Integer getCount(){
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT COUNT(id) AS cnt FROM travels");
            if (resultSet.next()){
                return resultSet.getInt("cnt");
            }
        } catch (SQLException e) {
            throw new DbException("Can't get count of travel in Db",e);
        }
        return null;
    }
    public Integer getCountTravelWithLocationId(Integer locationId){
        String sql = "SELECT COUNT(travel_id) AS cnt FROM travel_location WHERE location_id = ?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,locationId);
            ResultSet result = ps.executeQuery();
            if (result.next()){
                return result.getInt("cnt");
            }
        } catch (SQLException e) {
            throw new DbException("travel with locationId:" + locationId + " not exist",e);
        }
        return null;
    }
    public List<Travel> getAllTravels() { // норм
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
                        .authorId(userDao.getById(result.getInt("author_id")) != null
                                ? userDao.getById(result.getInt("author_id")).getId() : null)
                        .build();
                travels.add(travel);
            }
            return travels;
        } catch (SQLException e) {
            throw new DbException("Can't get travel list from db", e);
        }
    }

    public Travel getById(int travelId) { // норм
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
                        .authorId(user != null ? user.getId() : null )
                        .build();
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException("Can't get travel list from db", e);
        }
    }
    public Integer save(Travel travel){ // норм
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
    public List<Travel> getTravelsByLocationId(Integer locationId){
        List<Travel> travels = new ArrayList<>();
        String sql = "SELECT t.id, t.name, t.description, t.duration, t.author_id " +
                "FROM travel_location tl " +
                "JOIN travels t ON tl.travel_id = t.id " +
                "WHERE tl.location_id = ?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,locationId);
            ResultSet result = ps.executeQuery();
            while (result.next()){
                travels.add(Travel.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .description(result.getString("description"))
                        .duration(result.getString("duration"))
                        .authorId(result.getInt("author_id"))
                        .build());
            }
            return travels;
        } catch (SQLException e) {
            throw new DbException("",e);
        }
    }
    public List<Travel> getTravelsByUserId(Integer userId){
        String sql = "SELECT * FROM travels WHERE author_id =?";
        List<Travel> travels = new ArrayList<>();
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet result = ps.executeQuery();
            while (result.next()){
                travels.add(Travel.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .description(result.getString("description"))
                        .description(result.getString("description"))
                        .authorId(result.getInt("author_id"))
                        .build());
            }
            return travels;
        } catch (SQLException e) {
            throw new DbException("Travels with userId = %s not exist".formatted(userId),e);
        }
    }
    public void delete(Integer travelId){
        String sql = "DELETE FROM travels WHERE id=?";
        try(Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,travelId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't delete travel with id = %s".formatted(travelId),e);
        }
    }
    public boolean update(Travel travel){
        String sql = "UPDATE travels SET name=?, description=?,duration=? WHERE id=?";
        try(Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,travel.getName());
            ps.setString(2,travel.getDescription());
            ps.setString(3,travel.getDuration());
            ps.setInt(4,travel.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DbException("Can't update travel information with id = %s".formatted(travel.getId()),e);
        }
    }
    public void deleteInfoTravelLocation(Integer travel_id){
        String sql = "DELETE FROM travel_location WHERE travel_id=?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,travel_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't delete relation travel_location travelId = %s".formatted(travel_id),e);
        }
    }
}
