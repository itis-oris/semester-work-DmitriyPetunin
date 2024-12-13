package ru.kpfu.itis.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Integer id;
    private String name;
    private String country;
}
