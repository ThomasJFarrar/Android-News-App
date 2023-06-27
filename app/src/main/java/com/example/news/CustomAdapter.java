package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A custom adapter for {@link CustomViewHolder}.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private final Context context;
    private final List<News> news;
    private final SelectListener listener;

    /**
     * Constructor for the CustomAdapter.
     * @param context The application environment.
     * @param news A list of news articles.
     * @param listener A listener if a news article is clicked.
     */
    public CustomAdapter(Context context, List<News> news, SelectListener listener) {
        this.context = context;
        this.news = news;
        this.listener = listener;
    }

    /**
     * Sets up the custom view holder.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     */
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.news_list_items, parent, false), context, news);
    }

    /**
     * Sets the correct data to the view.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.text_title.setText(news.get(position).getTitle());
        holder.text_source.setText(news.get(position).getSource_id());
        holder.img_url.setText(news.get(position).getImage_url());
        if (news.get(position).getImage_url()!=null){
            Picasso.get().load(news.get(position).getImage_url()).into(holder.img_news);
        }

        holder.cardView.setOnClickListener(view -> listener.OnNewsClicked(news.get(position)));
    }

    /**
     * @return The number of items currently available in adapter.
     */
    @Override
    public int getItemCount() {
        return news.size();
    }
}
