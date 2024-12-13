package ru.kpfu.itis.service;

import jakarta.servlet.http.HttpServletRequest;
import org.eclipse.tags.shaded.org.apache.regexp.RE;
import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.dao.UserDao;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.dto.UserTravelDto;
import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.entity.User;
import ru.kpfu.itis.util.DbException;
import ru.kpfu.itis.util.TravelNotFoundException;

import java.util.ArrayList;
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
}
