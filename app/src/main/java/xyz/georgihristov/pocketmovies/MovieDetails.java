package xyz.georgihristov.pocketmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieDetails extends AppCompatActivity {


    private ActorsAdapter adapter;
    private String movieTrailerKey;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        View v = getWindow().getDecorView().getRootView();


        TextView movieNameTextView = (TextView) findViewById(R.id.movieTitle);
        TextView reviewTextView = (TextView) findViewById(R.id.reviewTextView);
        ImageView moviePoster = (ImageView) findViewById(R.id.mainPosterImageVIew);
        RecyclerView actorsList = (RecyclerView) findViewById(R.id.actorsView);
        ImageView trailerImage = (ImageView) findViewById(R.id.trailerImage);


        Intent intent = getIntent();

        int movieId = intent.getIntExtra("MOVIE_ID", 0);
        String title = intent.getStringExtra("MOVIE_NAME");
        final String poster = intent.getStringExtra("MOVIE_POSTER");
        final String POSTER_PATH = Api.BACKDROP_PATH + poster;
        movieNameTextView.setText(title);
        Picasso.with(this).load(POSTER_PATH).into(moviePoster);

        new ActorsExecutor().execute(String.format(Api.GET_CAST, movieId));
        try {
            movieTrailerKey = new VideoExecutor()
                    .execute(String.format(Api.GET_TRAILERS + Api.API_KEY, movieId))
                    .get();
            String review = new ReviewExecutor().execute(String.format(Api.GET_REVIEWS + Api.API_KEY, movieId)).get();
            if (review.length() < 3) {
                reviewTextView.setText("No Reviews Available");
            } else {
                reviewTextView.setText(review);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        final String thumbNail = String.format(Api.YOUTUBE_THUMBNAIL_URL, movieTrailerKey);
        Picasso.with(this).load(thumbNail).resize(1000, 0).into(trailerImage);

        adapter = new ActorsAdapter(MovieDetails.this, v, new ArrayList<Cast>());

        actorsList.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        actorsList.setLayoutManager(layoutManager);


        moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetails.this, FullScreenImage.class);
                intent.putExtra("IMAGE_", POSTER_PATH);
                startActivity(intent);
            }
        });
        trailerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youTubeTrailer = String.format(Api.YOUTUBE_VIDEO_URL, movieTrailerKey);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youTubeTrailer)));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class ActorsExecutor extends AsyncTask<String, List<Cast>, Void> {
        final Downloader downloader = new Downloader();

        @Override
        protected Void doInBackground(String... params) {
            String apiToGet = params[0];
            try {
                Actor actorResults = downloader.actorsResults(apiToGet);
                publishProgress(actorResults.getCast());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(List<Cast>... values) {
            super.onProgressUpdate(values);
            adapter.castList.addAll(values[0]);
            adapter.notifyDataSetChanged();
        }
    }

    private class VideoExecutor extends AsyncTask<String, List<VideoResult>, String> {
        final Downloader downloader = new Downloader();

        @Override
        protected String doInBackground(String... params) {
            String apiToGet = params[0];
            String stringToReturn = "";
            try {
                Video videoResult = downloader.videoResults(apiToGet);
                publishProgress(videoResult.getResults());
                for (VideoResult v : videoResult.getResults()) {
                    stringToReturn = v.getKey();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringToReturn;
        }
    }

    private class ReviewExecutor extends AsyncTask<String, List<Review>, String> {
        Downloader downloader = new Downloader();

        @Override
        protected String doInBackground(String... params) {
            String apiToGet = params[0];
            String stringToReturn = "";

            try {
                ReviewResult reviewResult = downloader.reviewResult(apiToGet);
                for (Review r : reviewResult.getResults()) {
                    stringToReturn = "Author: " + r.getAuthor() + "\n " + r.getContent();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return stringToReturn;
        }
    }
}
