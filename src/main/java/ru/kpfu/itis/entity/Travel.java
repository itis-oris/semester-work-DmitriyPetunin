package ru.kpfu.itis.entity;

import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Travel {
    private Integer id;
    private String name;
    private String description;
    private String duration;
    private Integer authorId;
    //private Set<Destination> destinations;
}