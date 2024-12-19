package ru.kpfu.itis.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private Date dateOfBirth;
}
