package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.LocationDao;
import ru.kpfu.itis.dto.LocationDto;
import ru.kpfu.itis.entity.Location;

import java.util.Set;
import java.util.stream.Collectors;

public class LocationService {

    private LocationDao locationDao = LocationDao.getInstance();

    public Integer save(LocationDto location){
        return locationDao.save(Location.builder()
                .name(location.getName())
                .country(location.getCountry())
                .build());
    }
    public Set<LocationDto> getLocationsByTravelId(int travel_id){
        return locationDao.getLocationsByTravelId(travel_id).stream()
                .map(location -> LocationDto.builder()
                                .name(location.getName())
                                .country(location.getCountry())
                                .build()
                        )
                .collect(Collectors.toSet());
    }
    public void addLocationToTravel(Integer travel_id,Integer location_id){
        locationDao.addLocationToTravel(travel_id,location_id);
    }
}
