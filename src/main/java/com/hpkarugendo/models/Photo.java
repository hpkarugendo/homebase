package com.hpkarugendo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Photo {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String url;
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] data;

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", name ='" + name + '\'' +
                ", size ='" + getData().length/1024 + "KBs" + '\'' +
                '}';
    }
}
