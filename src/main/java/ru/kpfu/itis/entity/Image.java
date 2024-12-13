package ru.kpfu.itis.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    private Integer id;
    private Integer travel_id;
    private String image_url;
}
