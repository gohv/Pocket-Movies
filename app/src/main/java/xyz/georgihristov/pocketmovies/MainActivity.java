package xyz.georgihristov.pocketmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView movieList;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = (ListView) findViewById(R.id.movieList);


        adapter = new MovieListAdapter(this, new ArrayList<Result>());

        new Executor().execute();

        movieList.setAdapter(adapter);

    }

    private class Executor extends AsyncTask<Void,List<Result>,Void>{

        Downloader downloader = new Downloader();

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Movie movieResults = downloader.movieResults();
                publishProgress(movieResults.getResults());


                for (Result m : movieResults.getResults()){
                    Log.d("JASON", m.getTitle());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(List<Result>... values) {
            adapter.movies.addAll(values[0]);
            adapter.notifyDataSetChanged();
        }
    }
}
