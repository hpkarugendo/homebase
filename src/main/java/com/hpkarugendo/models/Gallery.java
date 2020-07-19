package com.hpkarugendo.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Gallery {
    @Id
    private final String id = UUID.randomUUID().toString();
    private String name;
    @OneToMany(mappedBy = "gallery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos;

    public Gallery() {
    }

    public Gallery(String name) {
        this.name = name;
    }

    public Gallery(String name, List<Photo> photos) {
        this.name = name;
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", photos=" + photos +
                '}';
    }
}
