package ru.kpfu.itis.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTravelDto {
    private Integer id;
    private String name;
    private Integer age;
}
