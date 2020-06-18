package com.cornicosia.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
public class PostDetail extends AppCompatActivity {
private ImageView postImage;
private TextView postTitle, postDescription, postPublishDate, postAuthor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        init();
    }

    private void init() {
        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        postTitle=findViewById(R.id.post_title);
        postImage=findViewById(R.id.post_featured_image);
        postDescription=findViewById(R.id.post_Description);
        postPublishDate=findViewById(R.id.post_publish_date);
        Log.d("postimageurl",getIntent().getStringExtra("post_featured_image"));
        postAuthor=findViewById(R.id.post_author);
        if (getIntent().hasExtra("post_title")) {
            postTitle.setText(getIntent().getStringExtra("post_title"));
            postDescription.setText(getIntent().getStringExtra("post_description"));
            postPublishDate.setText(getIntent().getStringExtra("post_publish_date"));
            postAuthor.setText(getIntent().getStringExtra("post_author"));
            Glide.with(this)
                    .load(getIntent().getStringExtra("post_featured_image"))
                    .into(postImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}