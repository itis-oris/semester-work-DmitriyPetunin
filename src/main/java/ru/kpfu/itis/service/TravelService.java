package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.TravelDao;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.entity.Travel;
import ru.kpfu.itis.util.DbException;
import ru.kpfu.itis.util.TravelNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TravelService {
    private final TravelDao travelDao = TravelDao.getInstance();

    public TravelDto getDetail(String id) throws DbException {
        Travel travel = travelDao.getDetail(Integer.parseInt(id));
        if (travel == null){
            throw new TravelNotFoundException("travel not found");
        }
        return new TravelDto(Integer.parseInt(id),travel.getName(),travel.getDuration(),travel.getAuthor());
    }

    public int getCount(){
        return travelDao.getCount();
    }
    public List<TravelDto> getAllTravels(){
        return travelDao.getAllTravels()
                .stream()
                .map(travel -> {
                    return new TravelDto(
                            travel.getId(),
                            travel.getName(),
                            travel.getDuration(),
                            travel.getAuthor());
                })
                .collect(Collectors.toList());
    }
}
