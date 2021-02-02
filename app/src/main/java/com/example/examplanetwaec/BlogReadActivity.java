package com.example.examplanetwaec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BlogReadActivity extends AppCompatActivity {
    private session mySession;
    private String title, url, content;
    private LinearLayout blogimage;
    private TextView blog_header, blog_content;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_read);
        mySession = new session(getApplicationContext());
        if (mySession.checkLog() == false) {
            Intent intent = new Intent(BlogReadActivity.this, splashActivity.class);
            startActivity(intent);

        }

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");
        content = intent.getStringExtra("content");

        blogimage = findViewById(R.id.blogimage);
        blog_header = findViewById(R.id.blog_header);
        blog_content = findViewById(R.id.blog_content);
        back = findViewById(R.id.back);

        final ImageView img = new ImageView(this);
        Picasso.get()
                .load(url)
                .into(img, new Callback() {

                    @Override
                    public void onSuccess() {
                        blogimage.setBackground(img.getDrawable());
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        blog_header.setText(title);
        blog_content.setText(Html.fromHtml(content));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}