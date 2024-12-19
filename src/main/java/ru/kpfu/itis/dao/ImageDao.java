package ru.kpfu.itis.dao;

import ru.kpfu.itis.entity.Image;
import ru.kpfu.itis.util.ConnectionProvider;
import ru.kpfu.itis.exception.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ImageDao {

    private static ImageDao instance;

    public static ImageDao getInstance(){
        if (instance == null){
            return instance = new ImageDao();
        }
        return instance;
    }
    private ImageDao(){}

    public void save(Image image){
        String sql = "INSERT INTO images (travel_id,image_url) VALUES(?,?)";
        try(Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setInt(1,image.getTravel_id());
            pt.setString(2, image.getImage_url());
            pt.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Can't save image into db",e);
        }
    }
    public List<Image> getImagesByTravelId(Integer travel_id){
        String sql = "SELECT * FROM images WHERE travel_id = ?";
        try (Connection connection = ConnectionProvider.getInstance().getCon()){
            PreparedStatement pt = connection.prepareStatement(sql);
            List<Image> images = new ArrayList<>();
            pt.setInt(1,travel_id);
            ResultSet result = pt.executeQuery();
            while (result.next()){
                Image image = Image.builder()
                        .id(result.getInt("id"))
                        .travel_id(result.getInt("travel_id"))
                        .image_url(result.getString("image_url"))
                        .build();
                images.add(image);
            }
            return images;
        } catch (SQLException e) {
            throw new DbException("image with such travel_id does not exist",e);
        }
    }
}
