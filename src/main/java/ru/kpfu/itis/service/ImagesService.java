package ru.kpfu.itis.service;

import ru.kpfu.itis.dao.ImageDao;
import ru.kpfu.itis.dto.ImageDto;
import ru.kpfu.itis.dto.TravelDto;
import ru.kpfu.itis.entity.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImagesService {

    private final ImageDao imageDao = ImageDao.getInstance();

    public void save(ImageDto image){
        imageDao.save(Image.builder()
                .travel_id(image.getTravel_id())
                .image_url(image.getImage_url())
                .build());
    }
    public List<ImageDto> getImagesByTravelId(Integer travelId) {
        return imageDao.getImagesByTravelId(travelId)
                .stream()
                .map(image -> ImageDto.builder()
                        .id(image.getId())
                        .travel_id(image.getTravel_id())
                        .image_url(image.getImage_url())
                        .build())
                .collect(Collectors.toList());
    }

    public void saveAllImages(List<ImageDto> imageDtoList){
        for (ImageDto imageDto: imageDtoList){
            save(imageDto);
        }
    }
    public List<String> getFirstImageUrlList(List<TravelDto> travelDtos){
        List<String> images = new ArrayList<>();
        for (TravelDto travelDto : travelDtos){
            if (!getImagesByTravelId(travelDto.getId()).isEmpty()){
                images.add(getImagesByTravelId(travelDto.getId()).getFirst().getImage_url());
            } else{
                images.add(null);
            }
        }
        return images;
    }
}
