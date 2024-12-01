package ru.kpfu.itis.dto;

import java.util.Objects;

public class TravelDto {
    private Integer id;
    private String name;
    private String duration;
    private String author;

    public TravelDto(Integer id, String name, String duration, String author) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.author = author;
    }

    public TravelDto(String name, String duration, String author) {
        this.name = name;
        this.duration = duration;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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
