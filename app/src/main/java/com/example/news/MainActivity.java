package com.example.news;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.models.NewsApiResponse;
import com.example.news.models.News;

import java.util.List;

/**
 * The main news feed activity that displays the articles.
 */
public class MainActivity extends AppCompatActivity implements SelectListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RequestManager manager;

    /**
     * Sets up the activity.
     * @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Latest UK News");

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);

        swipeRefreshLayout = findViewById(R.id.swipelayout);

        manager = new RequestManager(this);
        manager.getNews(listener);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            manager.getNews(listener);
            swipeRefreshLayout.setRefreshing(false);
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean notifications = sharedPref.getBoolean("notifications", false);
        pushNotification(notifications);
    }

    private final OnFetchDataListener<NewsApiResponse> listener =
            new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<News> list, String message) {
            showNews(list);
        }

        @Override
        public void onError(String message) {
        }
    };

    /**
     * Sets up the menu.
     * @param menu The menu.
     * @return Whether the menu was created correctly
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);
        return true;
    }

    /**
     * When an option in the menu is selected.
     * @param item The menu item.
     * @return The item selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                openSaved();
                return true;
            case R.id.item2:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Opens the saved activity.
     */
    public void openSaved() {
        Intent intent = new Intent(this, SavedActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the settings activity.
     */
    public void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", news));
    }

    /**
     * Creates a notification channel for notifications.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotifyChannel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription("Description");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Sends out a notification to the user after 12 hours.
     * @param notification The notification.
     */
    private void pushNotification(Boolean notification) {
        if (notification) {
            System.out.println("test");
            createNotificationChannel();
            Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    MainActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            /* Sets the amount of time before sending out the notification */
            long timeAtCheck = System.currentTimeMillis();
            int hours = 12;
            long hoursInMillis = 3600000 * hours;

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    timeAtCheck + hoursInMillis,
                    pendingIntent);
        }
    }

}