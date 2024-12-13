package ru.kpfu.itis.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TravelDto {
    private Integer id;
    private String name;
    private String description;
    private String duration;
    private UserTravelDto author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelDto travelDto = (TravelDto) o;
        return Objects.equals(id, travelDto.id) && Objects.equals(name, travelDto.name) && Objects.equals(duration, travelDto.duration) && Objects.equals(author, travelDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, author);
    }
}
