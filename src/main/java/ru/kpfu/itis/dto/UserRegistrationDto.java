package ru.kpfu.itis.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private Integer age;
}
