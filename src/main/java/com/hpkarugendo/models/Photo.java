package com.hpkarugendo.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Photo {
    @Id
    private final String id = UUID.randomUUID().toString();
    private String name;
    @ManyToOne
    private Gallery gallery;
    private String url;

    public Photo() {
    }

    public Photo(String name, Gallery gallery, String url) {
        this.name = name;
        this.gallery = gallery;
        this.url = url;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gallery=" + gallery +
                ", url='" + url + '\'' +
                '}';
    }
}
