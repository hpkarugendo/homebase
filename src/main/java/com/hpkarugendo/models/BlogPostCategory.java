package com.hpkarugendo.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class BlogPostCategory {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogPost> posts;

    public BlogPostCategory() {
    }

    public BlogPostCategory(String name) {
        this.name = name;
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

    public List<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "BlogPostCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", posts=" + posts +
                '}';
    }
}
