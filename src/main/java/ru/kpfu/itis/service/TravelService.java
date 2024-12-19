package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.exception.DbException;
import ru.kpfu.itis.exception.TravelNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class TravelService {
    private final TravelDao travelDao = TravelDao.getInstance();
    private final UserService userService = new UserService();

    public TravelDto getById(int id) throws DbException { // норм
        Travel travel = travelDao.getById(id);
        if (travel == null){
            throw new TravelNotFoundException("travel not found");
        }
        return TravelDto.builder()
                .id(travel.getId())
                .name(travel.getName())
                .description(travel.getDescription())
                .duration(travel.getDuration())
                .author(userService.getUserTravelDtoById(travel.getAuthorId()))
                .build();
    }

    public int getCount(){
        return travelDao.getCount();
    }
    public List<TravelDto> getAllTravels(){ // норм
        List<Travel> travels = travelDao.getAllTravels();
        return travels.stream()
                .map(travel -> TravelDto.builder()
                        .id(travel.getId())
                        .name(travel.getName())
                        .description(travel.getDescription())
                        .duration(travel.getDuration())
                        .author(userService.getUserTravelDtoById(travel.getAuthorId()))
                        .build())
                .collect(Collectors.toList());
    }
    public Integer save(TravelDto travelDto){ //норм
        return travelDao.save(Travel.builder()
                .name(travelDto.getName())
                .description(travelDto.getDescription())
                .duration(travelDto.getDuration())
                .authorId(travelDto.getAuthor().getId())
                .build());
    }
    public List<TravelDto> getTravelsByLocationId(Integer locationId){
        return travelDao.getTravelsByLocationId(locationId).stream()
                .map(travel -> TravelDto.builder()
                        .id(travel.getId())
                        .name(travel.getName())
                        .description(travel.getDescription())
                        .duration(travel.getDuration())
                        .author(userService.getUserTravelDtoById(travel.getAuthorId()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<TravelDto> getTravelsByLocationIdAndUserId(Integer locationId,Integer userId){
        return travelDao.getTravelsByLocationId(locationId).stream()
                .filter(travel -> travel.getAuthorId().equals(userId))
                .map(travel -> TravelDto.builder()
                        .id(travel.getId())
                        .name(travel.getName())
                        .description(travel.getDescription())
                        .duration(travel.getDuration())
                        .author(userService.getUserTravelDtoById(travel.getAuthorId()))
                        .build())
                .collect(Collectors.toList());
    }
    public List<TravelDto> getTravelsByUserId(Integer userId){
        return travelDao.getTravelsByUserId(userId).stream()
                .map(travel -> TravelDto.builder()
                        .id(travel.getId())
                        .name(travel.getName())
                        .description(travel.getDescription())
                        .duration(travel.getDuration())
                        .author(userService.getUserTravelDtoById(travel.getAuthorId()))
                        .build())
                .collect(Collectors.toList());
    }
    public void delete(Integer travelId){
        travelDao.delete(travelId);
        travelDao.deleteInfoTravelLocation(travelId);
    }

    public boolean updateInfo(TravelDto travelDto){
        return travelDao.update(Travel.builder()
                .id(travelDto.getId())
                .name(travelDto.getName())
                .description(travelDto.getDescription())
                .duration(travelDto.getDuration())
                .build());
    }
}
