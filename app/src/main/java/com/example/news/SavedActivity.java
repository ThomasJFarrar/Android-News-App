package com.example.news;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.models.News;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The saved articles activity.
 */
public class SavedActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Sets up the activity.
     * @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        getSupportActionBar().setTitle("Saved Articles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadData();
        swipeRefreshLayout = findViewById(R.id.savedswipelayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadData();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    /**
     * Loads the articles from the text file.
     */
    public void loadData() {
        List<News> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(openFileInput("saved.txt")));
            String json;
            while ((json = reader.readLine()) != null) {
                Gson gson = new Gson();
                News article = gson.fromJson(json, News.class);
                list.add(article);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showNews(list);
    }

    /**
     * Creates a new adapter to put the news articles into the custom view holder.
     * @param list A list of news articles.
     */
    private void showNews(List<News> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * If the news article is clicked, open the details activity.
     * @param news The news article.
     */
    @Override
    public void OnNewsClicked(News news) {
        startActivity(new Intent(SavedActivity.this, DetailsActivity.class)
                .putExtra("data", news));
    }
}