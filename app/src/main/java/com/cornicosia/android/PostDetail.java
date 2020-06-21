package com.cornicosia.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PostDetail extends AppCompatActivity {

    private ImageView postImage;
    private TextView postTitle, postDescription, postPublishDate, postAuthor, postPermalink;
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
        getSupportActionBar().setTitle(getIntent().getStringExtra("post_title"));
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.tab_bar_bg_color), PorterDuff.Mode.SRC_ATOP);

        FloatingActionButton sharePost = findViewById(R.id.share);
        sharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = getIntent().getStringExtra("post_permalink");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Autobad");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Invite via"));
            }
        });

        postTitle=findViewById(R.id.post_title);
        postImage=findViewById(R.id.post_featured_image);
        postDescription=findViewById(R.id.post_Description);
        postPublishDate=findViewById(R.id.post_publish_date);
        postAuthor=findViewById(R.id.post_author);
        if (getIntent().hasExtra("post_title")) {
            postTitle.setText(getIntent().getStringExtra("post_title"));
            //Set post title as toolbar text
            postDescription.setText(getIntent().getStringExtra("post_description"));
            postPublishDate.setText(getIntent().getStringExtra("post_publish_date")+"\n"+getIntent().getStringExtra("post_publish_time"));
            postAuthor.setText("By "+getIntent().getStringExtra("post_author"));
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