package com.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "POST")
public class Post {

    //In case we want to use sequence
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    //@SequenceGenerator(name="group_seq", sequenceName = "group_seq", allocationSize=50)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long postId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NAME")
    private User user;

    @Column(name = "POST_DESCRIPTION")
    private String title;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private Timestamp createDateTime;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<PostComment> comments = new ArrayList<>();

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "TEMPERATURE")
    private Double temperature;

    @Column(name = "CITY")
    private String city;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Long getPostId() {

        return postId;
    }

    public void setPostId(Long id) {
        this.postId = id;

    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {

        this.comments = comments;
        for (PostComment postComment : comments) {
            postComment.setPost(this);
        }
    }


    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    public Post(Long id, String desc, User user) {
        this.postId = id;
        this.setTitle(desc);
        this.user = user;
    }

    public Post(String desc) {

        this.setTitle(desc);
    }

    public Post() {

    }

}

