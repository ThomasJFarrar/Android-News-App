package com.example.news;

import com.example.news.models.News;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<News> list, String message);
    void onError(String message);
}
