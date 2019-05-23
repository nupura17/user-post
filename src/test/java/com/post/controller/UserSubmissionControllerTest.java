package com.post.controller;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;

import com.google.common.collect.Lists;
import com.post.ForeignKeyContraintException;
import com.post.dao.UserPostCommentDao;
import com.post.model.Post;
import com.post.model.PostComment;
import com.post.model.User;
import com.post.repository.PostRepository;
import com.post.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserSubmissionControllerTest {
    @Mock
    private UserPostCommentDao mockUserPostCommentDao;

    UserSubmissionController userSubmissionController;

    @Before
    public void init() {
        userSubmissionController = new UserSubmissionController();
        userSubmissionController.setUserPostCommentDao(mockUserPostCommentDao);
    }

    @Test
    public void testPostUserSubmissionSavesSuccessfully() {

        User user = new User();
        user.setUserName("test");
        when(mockUserPostCommentDao.saveUserPost(user)).thenReturn(user);
        ResponseEntity<User> savedUser = userSubmissionController.postUserSubmission(user);

        verify(mockUserPostCommentDao,times(1)).saveUserPost(user);

        assertEquals(HttpStatus.OK, savedUser.getStatusCode());
    }







    @Test
    public void testPostUserSubmissionDoesntNotSavesSuccessfully() {

        User user = new User();
        user.setUserName("test");
        when(mockUserPostCommentDao.saveUserPost(user)).thenThrow(new ForeignKeyContraintException());
        ResponseEntity<User> savedUser = userSubmissionController.postUserSubmission(user);

        verify(mockUserPostCommentDao,times(1)).saveUserPost(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, savedUser.getStatusCode());
    }

    @Test
    public void testpostUserCommentsOnPostSavesSuccessfully() {

        Post mockPost = mock(Post.class);
        when(mockUserPostCommentDao.savePostComments(mockPost)).thenReturn(mockPost);
        ResponseEntity<Post> savedUser = userSubmissionController.postUserCommentsOnPost(mockPost);

        verify(mockUserPostCommentDao,times(1)).savePostComments(mockPost);

        assertEquals(HttpStatus.OK, savedUser.getStatusCode())
        ;
    }

    @Test
    public void testPostUserCommentsOnPostDoesntNotSavesSuccessfully() {

        Post mockPost = mock(Post.class);
        when(mockUserPostCommentDao.savePostComments(mockPost)).thenThrow(new ForeignKeyContraintException());
        ResponseEntity<Post> savedPost = userSubmissionController.postUserCommentsOnPost(mockPost);

        verify(mockUserPostCommentDao,times(1)).savePostComments(mockPost);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, savedPost.getStatusCode());
    }

}
