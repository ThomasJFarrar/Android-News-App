package com.example.news.models;

import java.io.Serializable;
import java.util.List;

/**
 * Model for the News API response.
 */
public class NewsApiResponse implements Serializable {
    List<News> results;

    public List<News> getArticles() {
        return results;
    }
}
