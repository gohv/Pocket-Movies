package xyz.georgihristov.pocketmovies;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Downloader{

    private final String URL_KEY = "http://api.themoviedb.org/3/discover/movie?language=en&sort_by=popularity.desc&api_key="+ Api.API_KEY;

    private OkHttpClient client;
    private Gson gson;


    public Downloader() {

        gson = new GsonBuilder().create();
        client = new OkHttpClient();
    }



    public Movie movieResults() throws Exception{
        Request request = new Request.Builder()
                .url(URL_KEY)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Something happened \n" + response);
        String json = response.body().string();
        Log.d("JASON", "RESPONSE " + json);

        return gson.fromJson(json,Movie.class);
    }
}
