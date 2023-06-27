package com.example.news;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.models.News;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Custom view holder for the news articles.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder{
    final TextView text_title, text_source, img_url;
    final ImageView img_news;
    final CardView cardView;
    final Button saveButton;
    final Context context;
    final List<News> newsList;

    /**
     * Constructor for the custom view holder.
     * @param itemView The view for the custom view holder.
     * @param context The application environment.
     * @param news A list of news articles.
     */
    public CustomViewHolder(@NonNull View itemView, Context context, List<News> news) {
        super(itemView);

        text_title = itemView.findViewById(R.id.text_title);
        text_source = itemView.findViewById(R.id.text_source);
        img_news = itemView.findViewById(R.id.img_news);
        cardView = itemView.findViewById(R.id.main_container);
        saveButton = itemView.findViewById(R.id.save_button);
        img_url = itemView.findViewById(R.id.img_url);
        newsList = news;
        this.context = context;
        /* Changes the button name of the article depending on the activity */
        if (context.getClass().getSimpleName().equals("SavedActivity")) {
            saveButton.setText("Unsave Article");
        } else {
            saveButton.setText("Save Article");
        }

        /* If the save/unsave article button is clicked */
        saveButton.setOnClickListener(view -> {
            if (context.getClass().getSimpleName().equals("SavedActivity")) {
                removeData();
            } else saveData();
        });
    }

    /**
     * Adds the an article to the text file.
     */
    public void saveData() {
        for (News article : newsList) {
            if (article.getTitle().equals(text_title.getText().toString())) {
                Gson gson = new Gson();
                String json = gson.toJson(article);
                System.out.println("test");
                try {
                    new File(context.getFilesDir().getPath() + "saved.txt");
                    FileOutputStream fOut =
                            context.openFileOutput("saved.txt", Context.MODE_APPEND);
                    fOut.write((json + "\n").getBytes());
                    fOut.close();
                    Toast.makeText(context, "Saved Article",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Rewrites the saved articles file, without an article.
     */
    public void removeData() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.openFileInput("saved.txt")));
            String json;
            StringBuilder sb = new StringBuilder();
            while ((json = reader.readLine()) != null) {
                Gson gson = new Gson();
                News article = gson.fromJson(json, News.class);
                if (!article.getTitle().contentEquals(text_title.getText())) {
                    sb.append(gson.toJson(article));
                    sb.append("\n");
                }
            }
            FileOutputStream fos = context.openFileOutput("saved.txt", Context.MODE_PRIVATE);
            fos.write(sb.toString().getBytes());
            fos.close();
            reader.close();
            Toast.makeText(context, "Unsaved Article",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
