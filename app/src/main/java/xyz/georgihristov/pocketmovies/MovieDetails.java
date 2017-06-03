package xyz.georgihristov.pocketmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity{


    private ActorsAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        TextView movieNameTextView = (TextView) findViewById(R.id.movieTitle);
        ImageView moviePoster = (ImageView) findViewById(R.id.mainPosterImageVIew);
        RecyclerView actorsList = (RecyclerView) findViewById(R.id.actorsView);



        Intent intent = getIntent();

        int id = intent.getIntExtra("MOVIE_ID",0);
        String title = intent.getStringExtra("MOVIE_NAME");
        final String poster = intent.getStringExtra("MOVIE_POSTER");
        final String POSTER_PATH = Api.BACKDROP_PATH+poster;
        movieNameTextView.setText(title);
        Picasso.with(this).load(POSTER_PATH).into(moviePoster);

        new ActorsExecutor().execute(String.format(Api.GET_CAST,id));

        adapter = new ActorsAdapter(MovieDetails.this, new ArrayList<Cast>());
        actorsList.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        actorsList.setLayoutManager(layoutManager);

        moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetails.this, FullScreenImage.class);

                intent.putExtra("IMAGE_",POSTER_PATH);
                startActivity(intent);
            }
        });
    }

    private class ActorsExecutor extends AsyncTask<String, List<Cast>, Void> {
        Downloader downloader = new Downloader();
        @Override
        protected Void doInBackground(String... params) {
            String apiToGet = params[0];
            try {
                Actor actorResults = downloader.actorsResults(apiToGet);
                publishProgress(actorResults.getCast());
                for (Cast ac : actorResults.getCast()){
                    Log.d("`",ac.getName());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(List<Cast>... values) {
            super.onProgressUpdate(values);
            adapter.castList.addAll(values[0]);
            adapter.notifyDataSetChanged();
        }
    }

    private class VideoExecutor extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //TODO: ADD TRAILER
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
