package com.post.model;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "USER_NAME")
    private String userName;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private Timestamp createDateTime;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Post> posts = new ArrayList<>();


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        for (Post post : posts) {
            post.setUser(this);
        }
    }


    public void addPost(Post post) {
        this.posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        post.setUser(this);
    }


    public User(String userName) {
        this.userName = userName;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return userName != null && userName.equalsIgnoreCase(((User) o).getUserName());
    }

    @Override
    public int hashCode() {
        return 8;
    }
}
