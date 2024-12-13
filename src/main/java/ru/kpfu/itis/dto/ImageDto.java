package ru.kpfu.itis.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Integer id;
    private Integer travel_id;
    private String image_url;
}
