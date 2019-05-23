package com.post.dao;


import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.*;

import com.google.common.collect.Lists;
import com.post.model.Post;
import com.post.model.PostComment;
import com.post.model.User;
import com.post.repository.PostRepository;
import com.post.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserPostCommentDaoTest {
    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PostRepository mockPostRepository;

    private UserPostCommentDao userPostCommentDao;

    @Before
    public void init() {
        userPostCommentDao = new UserPostCommentDao();
        userPostCommentDao.setPostRepository(mockPostRepository);
        userPostCommentDao.setUserRepository(mockUserRepository);
    }

    @Test
    public void testSaveUserPostWhenUserNotExist() {

        User user = new User();
        user.setUserName("test");
        when(mockUserRepository.findOne(user.getUserName())).thenReturn(null);
        when(mockUserRepository.save(user)).thenReturn(user);
        User savedUser = userPostCommentDao.saveUserPost(user);

        verify(mockUserRepository,times(1)).findOne(user.getUserName());
        verify(mockUserRepository,times(1)).save(user);

		assertEquals("test", savedUser.getUserName());
    }

    @Test
    public void testSaveUserPostWhenUserExist() {

        User saveUser = mock(User.class);
        User existingUserInstance = mock(User.class);
        User persistedUserInstance = mock(User.class);
        Post newPost = mock(Post.class);

        when(saveUser.getUserName()).thenReturn("test");
        when(saveUser.getPosts()).thenReturn(Lists.newArrayList(newPost));
        when(persistedUserInstance.getUserName()).thenReturn("test");


        when(mockUserRepository.findOne(saveUser.getUserName())).thenReturn(existingUserInstance);
        when(mockUserRepository.save(existingUserInstance)).thenReturn(persistedUserInstance);

        User savedUser = userPostCommentDao.saveUserPost(saveUser);

        verify(mockUserRepository,times(1)).findOne(saveUser.getUserName());
        verify(saveUser,times(1)).getPosts();
        verify(existingUserInstance,times(1)).addPost(newPost);
        verify(mockUserRepository,times(1)).save(existingUserInstance);

        assertEquals("test", savedUser.getUserName());
        assertEquals(persistedUserInstance,savedUser);
    }

    @Test
    public void testSaveUserPostWhenPostIsNew() {

        Post post = new Post();
        post.setTitle("test");
        post.setPostId(1L);
        when(mockPostRepository.findOne(post.getPostId())).thenReturn(null);
        when(mockPostRepository.save(post)).thenReturn(post);
        Post savedPost = userPostCommentDao.savePostComments(post);

        verify(mockPostRepository,times(1)).findOne(post.getPostId());
        verify(mockPostRepository,times(1)).save(post);

       assertThat(1L, is(savedPost.getPostId()));
    }

    @Test
    public void testSaveUserPostWhenPostIsExists() {

        Post savePost = mock(Post.class);
        Post existingPostInstance = mock(Post.class);
        Post persistedPostInstance = mock(Post.class);
        PostComment newPost = mock(PostComment.class);

        when(savePost.getPostId()).thenReturn(1L);
        when(savePost.getComments()).thenReturn(Lists.newArrayList(newPost));
        when(persistedPostInstance.getPostId()).thenReturn(1L);


        when(mockPostRepository.findOne(savePost.getPostId())).thenReturn(existingPostInstance);
        when(mockPostRepository.save(existingPostInstance)).thenReturn(persistedPostInstance);

        Post savedPost = userPostCommentDao.savePostComments(savePost);

        verify(mockPostRepository,times(1)).findOne(savePost.getPostId());
        verify(savePost,times(1)).getComments();
        verify(existingPostInstance,times(1)).addComment(newPost);
        verify(mockPostRepository,times(1)).save(existingPostInstance);

        assertThat(1L, is(savedPost.getPostId()));
        assertEquals(persistedPostInstance,savedPost);
    }
}

