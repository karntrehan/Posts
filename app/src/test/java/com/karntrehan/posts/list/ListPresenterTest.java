package com.karntrehan.posts.list;

import android.text.TextUtils;

import com.karntrehan.posts.base.callback.StatefulCallback;
import com.karntrehan.posts.list.entity.Post;
import com.karntrehan.posts.utils.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.karntrehan.posts.utils.TestUtil.print;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by karn on 04-06-2017.
 */
public class ListPresenterTest {

    @Mock
    private ListContract.Model model;

    @Mock
    private ListContract.View view;

    @Captor
    private ArgumentCaptor<StatefulCallback<List<Post>>> statefulCaptor;

    private ListPresenter presenter;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        presenter = new ListPresenter(model);
        presenter.setView(view);
    }

    @Test
    public void testCreate() {
        presenter.create();
        verify(model).loadPosts(statefulCaptor.capture());
        print("Create of presenter calls model to load posts");
    }

    @Test
    public void testSetView() {
        presenter.setView(view);
        assertEquals(presenter.isViewReady(), true);
        print("Setting view makes view ready to receive calls");
    }

    @Test
    public void testDestroy() {
        presenter.destroy();
        assertEquals(presenter.isViewReady(), false);
        print("Destroying makes view not ready to receive calls");
    }

    @Test
    public void testGetPostsLocal() {
        presenter.getPosts();
        verify(view).showLoading(true);
        verify(model).loadPosts(statefulCaptor.capture());

        List<Post> postsLocal = new ArrayList<>();
        postsLocal.add(new Post());
        postsLocal.add(new Post());

        statefulCaptor.getValue().onSuccessLocal(postsLocal);
        verify(view).showLoading(true);
        verify(view).showPosts(postsLocal);

        print("GetPosts shows posts but does not hide loading if results are only local");
    }

    @Test
    public void testGetPostsSync() {
        presenter.getPosts();
        verify(view).showLoading(true);
        verify(model).loadPosts(statefulCaptor.capture());

        List<Post> postsSync = new ArrayList<>();
        postsSync.add(new Post());
        postsSync.add(new Post());

        statefulCaptor.getValue().onSuccessSync(postsSync);
        verify(view).showLoading(false);
        verify(view).showPosts(postsSync);

        print("GetPosts shows posts and hides loading if results are from server");
    }

    @Test
    public void testGetPostsValidationError() {
        presenter.getPosts();
        verify(view).showLoading(true);
        verify(model).loadPosts(statefulCaptor.capture());

        Response<List<Post>> response = TestUtil.createErrorResponse(500);

        statefulCaptor.getValue().onValidationError(response);
        verify(view).showLoading(false);
        verify(view).showError("Error, Server returned: " + response.code());

        print("GetPosts shows error toast if server returns validation error");
    }

    @Test
    public void testShowPostDetails() {
        Post post = new Post(1, 1, "TestTitle", "TestBody");
        presenter.postClicked(1, post, null);
        verify(view).showPostDetail(1, post, null);

        print("Clicking on post successfully opens post details");
    }


}