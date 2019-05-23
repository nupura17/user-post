package com.post.dao;


import com.post.model.Post;
import com.post.model.PostComment;
import com.post.model.User;
import com.post.repository.PostRepository;
import com.post.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class UserPostCommentDao {
    private static final Logger logger = LoggerFactory.getLogger(UserPostCommentDao.class);

    UserRepository userRepository;

    PostRepository postRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public User saveUserPost(final User user) {
        User persistUser = user;
        User existingUser = userRepository.findOne(user.getUserName());
        if (existingUser != null) {
            for (Post post : user.getPosts()) {
                existingUser.addPost(post);
            }
            persistUser = existingUser;
        }

       return  userRepository.save(persistUser);
    }

    @Transactional
    public Post savePostComments(final Post post) {
        Post persistPost=post;
        Post existingPost = postRepository.findOne(post.getPostId());
        if(existingPost!=null){
            for (PostComment addPostComment : post.getComments()) {
                existingPost.addComment(addPostComment);
            }
            persistPost = existingPost;
        }
        return postRepository.save(persistPost);
    }

    /*@Transactional
    public List<Post> getAllUsersPost() {
        return  postRepository.findAll();
    }*/


}
