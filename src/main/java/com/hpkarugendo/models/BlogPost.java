package com.hpkarugendo.models;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class BlogPost {
    @Id
    @GeneratedValue
    private int id;
    private String imageUrl;
    private String title;
    @Temporal(TemporalType.DATE)
    private final Date date = new Date();
    private String exerpt;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private BlogPostCategory category;
    @ManyToOne
    private SiteAdmin author;
    private boolean isReady;

    public BlogPost() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExerpt() {
        return exerpt;
    }

    public void setExerpt(String exerpt) {
        this.exerpt = exerpt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogPostCategory getCategory() {
        return category;
    }

    public void setCategory(BlogPostCategory category) {
        this.category = category;
    }

    public SiteAdmin getAuthor() {
        return author;
    }

    public void setAuthor(SiteAdmin author) {
        this.author = author;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", category=" + category.getName() +
                ", author=" + author.getAdminFullName() +
                ", isReady=" + isReady +
                '}';
    }
}
