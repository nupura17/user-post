package com.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "PostComment")
@Table(name = "POST_COMMENTS")
public class PostComment {
    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue
    private Long commentId;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(name = "COMMENT")
    private String comment;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private Timestamp createDateTime;

    public PostComment() {
    }

    public PostComment(String comment, Post post) {
        this.comment = comment;
        this.post = post;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }


    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
    }


    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public PostComment(String comment) {

        this.comment = comment;
    }


}

