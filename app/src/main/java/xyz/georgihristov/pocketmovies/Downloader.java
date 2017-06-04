package xyz.georgihristov.pocketmovies;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class Downloader {

    private final OkHttpClient client;
    private final Gson gson;


    public Downloader() {

        gson = new GsonBuilder().create();
        client = new OkHttpClient();
    }


    public Movie movieResults(String api) throws Exception {
        Request request = new Request.Builder()
                .url(api)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Something happened \n" + response);
        String json = response.body().string();
        Log.d("JASON", "RESPONSE " + json);

        return gson.fromJson(json, Movie.class);
    }

    public Actor actorsResults(String api) throws Exception {
        Request request = new Request.Builder()
                .url(api)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Something happened \n" + response);
        String json = response.body().string();
        Log.d("JASON", "RESPONSE " + json);

        return gson.fromJson(json, Actor.class);
    }

    public Video videoResults(String api) throws Exception {
        Request request = new Request.Builder()
                .url(api)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Something happened \n" + response);
        String json = response.body().string();
        Log.d("JASON", "RESPONSE " + json);

        return gson.fromJson(json, Video.class);
    }


    public ReviewResult reviewResult (String api) throws Exception {
        Request request = new Request.Builder()
                .url(api)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Something happened \n" + response);
        String json = response.body().string();
        Log.d("JASON", "RESPONSE " + json);

        return gson.fromJson(json, ReviewResult.class);
    }



}
