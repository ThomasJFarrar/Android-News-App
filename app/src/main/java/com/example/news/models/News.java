package com.example.news.models;

import java.io.Serializable;

/**
 * A model for the News article.
 */
public class News implements Serializable {

    final String title;
    final String image_url;
    final String source_id;
    final String pubDate;
    final String description;
    final String link;

    /**
     * Constructor for the News object.
     * @param title Title of the article.
     * @param image_url Image url of the article.
     * @param source_id Source of the article.
     * @param pubDate Publish date of the article.
     * @param description Description of the article.
     * @param link URL link to the article.
     */
    public News(String title, String image_url, String source_id, String pubDate,
                String description, String link) {
        this.title = title;
        this.image_url = image_url;
        this.source_id = source_id;
        this.pubDate = pubDate;
        this.description = description;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getSource_id() {
        return source_id;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() { return link; }
}
