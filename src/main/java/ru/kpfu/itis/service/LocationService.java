package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.LocationDao;
import ru.kpfu.itis.dto.LocationDto;
import ru.kpfu.itis.entity.Location;
import ru.kpfu.itis.exception.DbException;
import ru.kpfu.itis.exception.LocationNotFoundException;
import ru.kpfu.itis.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LocationService {

    private final LocationDao locationDao = LocationDao.getInstance();

    public Integer save(LocationDto location){
        return locationDao.save(Location.builder()
                .name(location.getName())
                .country(location.getCountry())
                .build());
    }
    public Set<LocationDto> getLocationsByTravelId(int travel_id){
        return locationDao.getLocationsByTravelId(travel_id).stream()
                .map(location -> LocationDto.builder()
                                .id(location.getId())
                                .name(location.getName())
                                .country(location.getCountry())
                                .build()
                        )
                .collect(Collectors.toSet());
    }
    public void addLocationToTravel(Integer travel_id,Integer location_id){
        locationDao.addLocationToTravel(travel_id,location_id);
    }
    public void dropAllByTravelId(Integer travel_id){
        locationDao.dropAllByTravelId(travel_id);
    }
    public LocationDto getById(Integer locationId){
        Location location = locationDao.getById(locationId);
        if (location == null){
            throw new LocationNotFoundException("location not found");
        } else {
            return LocationDto.builder()
                    .id(location.getId())
                    .name(location.getName())
                    .country(location.getCountry())
                    .build();
        }
    }
    public Integer updateInfo(LocationDto locationDto, int travelId){
        Integer locationId;
        Integer locationExistInDb = check(locationDto,getLocationsByTravelId(travelId));
        if (locationExistInDb != null) {
            locationId = locationExistInDb;
        } else {
            locationId = locationDao.save(Location.builder()
                    .name(locationDto.getName())
                    .country(locationDto.getCountry())
                    .build());
        }
        return locationId;
    }
    public void updateTravelLocation(Integer travel_id, List<Integer> locationsIds){
        dropAllByTravelId(travel_id);
        for (Integer locationId : locationsIds){
            addLocationToTravel(travel_id,locationId);
        }
    }
    public Integer check(LocationDto locationDto,Set<LocationDto> locations){
        for (LocationDto loc : locations){
            if (loc.getName().equals(locationDto.getName())){
                return loc.getId();
            }
        }
        return null;
    }
}
