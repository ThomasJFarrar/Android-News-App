package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.news.models.News;

/**
 * Activity which displays details about a news article.
 */
public class DetailsActivity extends AppCompatActivity {
    News news;
    TextView text_title ;
    TextView text_source;
    TextView text_date;
    TextView text_description;
    TextView text_link;

    /**
     * Sets up the activity and details of the news article.
     * @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text_title = findViewById(R.id.article_title);
        text_source = findViewById(R.id.article_source);
        text_date = findViewById(R.id.article_date);
        text_description = findViewById(R.id.article_description);
        text_link = findViewById(R.id.article_link);

        news = (News) getIntent().getSerializableExtra("data");

        text_title.setText(news.getTitle());
        text_source.setText(news.getSource_id());
        text_date.setText(news.getPubDate());
        text_description.setText(news.getDescription());
        text_link.setText(news.getLink());

        /* If the link is clicked, open it in the web browser*/
        text_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(text_link.getText().toString()));
                startActivity(intent);
            }
        });
    }
}