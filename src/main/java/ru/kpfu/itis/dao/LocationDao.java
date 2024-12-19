package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Location;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.exception.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class LocationDao {

    private static LocationDao instance;

    public static LocationDao getInstance(){
        if (instance == null){
            return instance = new LocationDao();
        }
        return instance;
    }
    private LocationDao(){}

    public Location getById(Integer id){
        String sql = "SELECT * FROM locations WHERE id =?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet result = ps.executeQuery();
            if (result.next()){
                return Location.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .country(result.getString("country"))
                        .build();
            } else return null;
        } catch (SQLException e) {
            throw new DbException("",e);
        }
    }
    public Location getByName(String name){
        String sql = "SELECT * FROM locations WHERE name =?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet result = ps.executeQuery();
            if (result.next()){
                return Location.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .country(result.getString("country"))
                        .build();
            } else return null;
        } catch (SQLException e) {
            throw new DbException("",e);
        }
    }

    public Integer save(Location location){
        Integer locationId = null;
        String sql = "INSERT INTO locations(name,country) VALUES(?,?) RETURNING id";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,location.getName());
            ps.setString(2,location.getCountry());
            ResultSet result = ps.executeQuery();
            if (result.next()){
                locationId = result.getInt("id");
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")){
                return getByName(location.getName()).getId();
            } else {
                throw new DbException("Can't save location", e);
            }
        }
        return locationId;
    }
    public Set<Location> getLocationsByTravelId(int travel_id){
        Set<Location> locations = new HashSet<>();
        String sql = "SELECT l.id, l.name, l.country " +
                "FROM travel_location tl " +
                "JOIN locations l ON tl.location_id = l.id " +
                "WHERE tl.travel_id = ?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,travel_id);
            ResultSet result = ps.executeQuery();

            while (result.next()){
                locations.add(Location.builder()
                        .id(result.getInt("id"))
                        .name(result.getString("name"))
                        .country(result.getString("country"))
                        .build());
            }


        } catch (SQLException e) {
            throw new DbException("",e);
        }
        return locations;
    }
    public void addLocationToTravel(Integer travel_id,Integer location_id){
        String sql = "INSERT INTO travel_location (travel_id,location_id) VALUES(?,?)";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,travel_id);
            ps.setInt(2,location_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("",e);
        }
    }

    public void dropAllByTravelId(Integer travelId) {
        String sql = "DELETE FROM travel_location WHERE travel_id=?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,travelId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't update info in table travel_location",e);
        }
    }
}

