package com.post.controller;


import com.post.dao.UserPostCommentDao;
import com.post.model.Post;
import com.post.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("userPost/api/")
public class UserSubmissionController {
    private static final Logger logger = LoggerFactory.getLogger(UserSubmissionController.class);

    private UserPostCommentDao userPostCommentDao;

    @Autowired
    public void setUserPostCommentDao(UserPostCommentDao userPostCommentDao) {
        this.userPostCommentDao = userPostCommentDao;
    }

    @RequestMapping(value = "postUserSubmission", method = RequestMethod.POST,  produces = { "application/json" })
    public ResponseEntity<User> postUserSubmission(@RequestBody User user){
        ResponseEntity responseEntity;
        try {
            User persistedUser = userPostCommentDao.saveUserPost(user);
            responseEntity=new ResponseEntity<User>(persistedUser, HttpStatus.OK);
        }
        catch(Exception e) {
            logger.error("Error persisting user submission ",e);
            responseEntity=new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "postComments", method = RequestMethod.POST,  produces = { "application/json" })
    public ResponseEntity<Post> postUserCommentsOnPost(@RequestBody Post post){
        ResponseEntity responseEntity;
        try {
            Post persistedPost = userPostCommentDao.savePostComments(post);
            responseEntity = new ResponseEntity<Post>(persistedPost, HttpStatus.OK);
        }
        catch(Exception e) {
            logger.error("Error persisting post comments  ",e);
            responseEntity=new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /*@RequestMapping(value = "getPostsOfAllUsers", method = RequestMethod.GET,  produces = { "application/json" })
    public ResponseEntity<List<Post>> getUserCommentsOnPost(){
        ResponseEntity responseEntity;
        try {
            List<Post> existingPost = userPostCommentDao.getAllUsersPost();
            responseEntity = new ResponseEntity<List<Post>>(existingPost, HttpStatus.OK);
        }
        catch(Exception e) {
            logger.error("Error  fetching posts  ",e);
            responseEntity=new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }*/
}
