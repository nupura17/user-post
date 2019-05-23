package com.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.post.App;
import com.post.model.Post;
import com.post.model.PostComment;
import com.post.model.User;
import com.post.repository.PostRepository;
import com.post.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest
@TestPropertySource("/application-test.properties")
public class UserSubmissionControllerIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(UserSubmissionControllerIntegrationTest.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public final static String url = "http://localhost:8081/userPost/api/";

    RestTemplate restTemplate;
    HttpHeaders requestHeaders;

    @Before
    public void init() {
        userRepository.deleteAll();
        restTemplate = new RestTemplate();
        requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void testPostUserSubmissionApiToCreateNewUserPost() throws IOException {
        User requestBody = getUserObjectToSave("Nupur");
        requestBody.setPosts(Lists.newArrayList(getPost("My First Post!!!!!")));


        HttpEntity<String> httpEntity =
                new HttpEntity<String>(objectMapper.writeValueAsString(requestBody), requestHeaders);

        User user = restTemplate.postForObject(url + "postUserSubmission", httpEntity, User.class);
        assertNotNull(user);
        assertEquals(user.getPosts().size(),1);
    }

    @Test
    public void testPostUserSubmissionApiToAddPostToExistingUser() throws IOException {

        User persistOneUser = getUserObjectToSave("Nupur");
        persistOneUser.setPosts(Lists.newArrayList(getPost("My First Post!!!!!")));
        userRepository.save(persistOneUser);


        User requestBody = getUserObjectToSave("Nupur");
        requestBody.addPost(getPost("My Second Post!!!!!"));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(objectMapper.writeValueAsString(requestBody), requestHeaders);

        User user = restTemplate.postForObject(url + "postUserSubmission", httpEntity, User.class);
        assertThat(2, is(user.getPosts().size()));
        assertEquals("My First Post!!!!!", user.getPosts().get(0).getTitle());
        assertEquals("My Second Post!!!!!", user.getPosts().get(1).getTitle());

    }

    @Test
    public void testPostCommentsSubmissionApiToCreateNewUserPost() throws IOException {
        User persistOneUser = getUserObjectToSave("Nupur");
        persistOneUser.setPosts(Lists.newArrayList(getPost("My First Post!!!!!")));
        User persistedOneUser = userRepository.save(persistOneUser);
        Long postId = persistedOneUser.getPosts().get(0).getPostId();

        Post requestBody = new Post();
        requestBody.setPostId(postId);
        PostComment postComment = getPostComment("My First Comment to First post");
        requestBody.setComments(Lists.newArrayList(postComment));

        HttpEntity<String> httpEntity =
                new HttpEntity<String>(objectMapper.writeValueAsString(requestBody), requestHeaders);

        Post savedPostComments = restTemplate.postForObject(url + "postComments", httpEntity, Post.class);
        assertEquals("My First Comment to First post", savedPostComments.getComments().get(0).getComment());
        assertNotNull(savedPostComments.getComments().get(0).getCommentId());
        assertEquals(savedPostComments.getComments().size(),1);
    }


    private User getUserObjectToSave(String userName) {

        User user = new User();
        user.setUserName(userName);
        return user;
    }

    private Post getPost(String title) {
        Post post = new Post();
        post.setTitle(title);
        return post;
    }


    private PostComment getPostComment(String comment) {
        PostComment postComment = new PostComment();
        postComment.setComment(comment);
        return postComment;
    }


}
