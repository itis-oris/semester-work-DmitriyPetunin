package ru.kpfu.itis.entity;

import java.util.Objects;

public class Travel {
    private Integer id;
    private String name;
    private String duration;
    private String author;

    public Travel(String name, String duration, String author) {
        this.id = null;
        this.name = name;
        this.duration = duration;
        this.author = author;
    }


    public Travel(Integer id, String name, String duration, String author) {
        this.id = id;
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
        Travel travel = (Travel) o;
        return Objects.equals(id, travel.id) && Objects.equals(name, travel.name) && Objects.equals(duration, travel.duration) && Objects.equals(author, travel.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, author);
    }
}