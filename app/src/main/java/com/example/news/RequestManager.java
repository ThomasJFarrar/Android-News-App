package com.example.news;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.example.news.models.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Handles the API requests.
 */
public class RequestManager {
    final Context context;
    String countryCode = "gb";
    BroadcastReceiver broadcastReceiver = null;
    final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://newsdata.io/api/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * Gets the news from the API.
     * @param listener The listener for getting news.
     */
    public void getNews(OnFetchDataListener listener) {
        setCountryCode();
        broadcastReceiver = new InternetReceiver();
        internetStatus();
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call =
                callNewsApi.callHeadlines(context.getString(R.string.api_key), countryCode);

        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Could not retrieve news", Toast.LENGTH_SHORT).show();
                    }

                    listener.onFetchData(response.body().getArticles(), response.message());
                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listener.onError("Request Failed");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallNewsApi {
        @GET("news")
        Call<NewsApiResponse> callHeadlines(
                @Query("apiKey") String api_key,
                @Query("country") String country
                );
    }

    /**
     * Registers the receiver for checking internet connection.
     */
    public void internetStatus(){
        context.registerReceiver(
                broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Sets the country code when fetching the news.
     */
    public void setCountryCode(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String country = sharedPref.getString("country", "gb");
        switch (country) {
            case "United Kingdom":
                countryCode = "gb";
                break;
            case "China":
                countryCode = "cn";
                break;
            case "France":
                countryCode = "fr";
                break;
            case "Germany":
                countryCode = "de";
                break;
            case "Russia":
                countryCode = "ru";
                break;
            case "Spain":
                countryCode = "es";
                break;
            case "United States":
                countryCode = "us";
                break;
        }
    }
}
