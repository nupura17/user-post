package com.post.repository;

import com.google.common.collect.Lists;
import com.post.App;
import com.post.model.Post;
import com.post.model.PostComment;
import com.post.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)

public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    @Before
    public void init() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();

    }

    @Test
    public void testFindAll() {
        List<User> users = userRepository.findAll();
        assertThat(users.size(), is(greaterThanOrEqualTo(0)));
    }


    @Test
    public void testFindOne() {
        User saveUser = getUserObjectToSave("Nupur");
        saveUser.setPosts(Lists.newArrayList(getPost("My First Post!!!!!")));
        User persistedUser=userRepository.save(saveUser);
        Long postId = persistedUser.getPosts().get(0).getPostId();
        assertNotNull(persistedUser);
        assertEquals(persistedUser.getPosts().size(),1);

        User foundUser = userRepository.findOne("Nupur");
        assertNotNull(foundUser);
        assertEquals(foundUser.getPosts().size(),1);
        assertEquals(foundUser.getPosts().get(0).getTitle(),"My First Post!!!!!");

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



}
