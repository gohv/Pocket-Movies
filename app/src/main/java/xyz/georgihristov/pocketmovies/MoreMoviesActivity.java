package xyz.georgihristov.pocketmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MoreMoviesActivity extends AppCompatActivity {

    private static final String TAG = MoreMoviesActivity.class.getName();
    private RecyclerView movieList;
    private MovieRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int id = intent.getIntExtra("PERSONID", 0);
        String apiToGet = String.format(Api.GET_MORE_MOVIES,id);

        movieList = (RecyclerView) findViewById(R.id.movieList);
        loadList(apiToGet);
        movieList.setLayoutManager(new GridLayoutManager(this, 2));






    }
    private void loadList(String json) {

        adapter = new MovieRecyclerAdapter(MoreMoviesActivity.this, new ArrayList<Result>());
        new Executor().execute(json);
        movieList.setAdapter(adapter);


    }
    private class Executor extends AsyncTask<String, List<Result>, Void> {
        final Downloader downloader = new Downloader();
        @Override
        protected Void doInBackground(String... params) {
            String apiToGet = params[0];
            try {
                MoreMoviesForActor movieResults = downloader.moreMoviesResult(apiToGet);
                publishProgress(movieResults.getCast());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @SafeVarargs
        @Override
        protected final void onProgressUpdate(List<Result>... values) {
            List<Result> results = new ArrayList<>();
            results.addAll(values[0]);
            for (Result s : results){
                Log.d(TAG,s.getOriginalTitle());
            }
            adapter.movies.addAll(values[0]);
            adapter.notifyDataSetChanged();
        }
    }
}
