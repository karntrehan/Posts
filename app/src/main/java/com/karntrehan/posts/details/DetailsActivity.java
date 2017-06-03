package com.karntrehan.posts.details;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.karntrehan.posts.PostApp;
import com.karntrehan.posts.R;
import com.karntrehan.posts.base.Constants;
import com.karntrehan.posts.databinding.ActivityDetailsBinding;
import com.karntrehan.posts.list.entity.Post;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import static android.R.attr.id;
import static android.R.attr.visibility;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.View {

    ActivityDetailsBinding binding;
    Post post;

    @Inject
    DetailsContract.Presenter presenter;

    @Inject
    Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        setUpToolbar();
        handleExtras();

        ((PostApp) this.getApplication()).createDetailComponent().inject(this);

        binding.tvCommentCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailsActivity.this, "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpToolbar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getPostDetails(post);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.destroy();
    }


    private void handleExtras() {
        Bundle extras = getIntent().getExtras();
        post = extras.getParcelable(Constants.POST_SEL);
        if (post == null)
            finish();
        binding.tvTitle.setText(post.getPostTitle());
        binding.tvBody.setText(post.getPostBody());
        handleTransition(extras);
    }

    private void handleTransition(Bundle extras) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String titleTransactionName = extras.getString(Constants.TITLE_TRANSITION_NAME);
            String bodyTransitionName = extras.getString(Constants.BODY_TRANSITION_NAME);
            binding.tvTitle.setTransitionName(titleTransactionName);
            binding.tvBody.setTransitionName(bodyTransitionName);
        }
    }

    ////////////////////////////////////////////////////////////////////
    //// View interaction methods
    ////////////////////////////////////////////////////////////////////

    @Override
    public void showPostDetails(Details details) {
        if (details.userName != null) {
            binding.rlUserDetails.setVisibility(View.VISIBLE);
            binding.tvAuthorName.setText(details.userName);
            picasso.load(details.userAvatar).placeholder(R.drawable.loading).into(binding.ivAvatar);
        }
        if (binding.tvCommentCounter != null) {
            binding.tvCommentCounter.setVisibility(View.VISIBLE);
            binding.tvCommentCounter.setText("See all " + details.commentCount + " comments >");
        }
    }

    @Override
    public void showLoading(boolean loading) {
        if (loading)
            binding.pbLoading.setVisibility(View.VISIBLE);
        else binding.pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
