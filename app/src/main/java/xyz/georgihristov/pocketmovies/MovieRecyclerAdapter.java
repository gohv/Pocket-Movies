package xyz.georgihristov.pocketmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {

    private final Context context;
    public final List<Result> movies;


    public MovieRecyclerAdapter(Context context, List<Result> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Result movie;
        ImageView moviePoster; //public by default
        TextView movieName;
        TextView movieOverview;
        TextView movieScore;

        public MovieViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(this);
            movieName = (TextView) itemView.findViewById(R.id.movieName);
            movieScore = (TextView) itemView.findViewById(R.id.movieScore);
            movieOverview = (TextView) itemView.findViewById(R.id.movieOverview);
            moviePoster = (ImageView) itemView.findViewById(R.id.moviePoster);

            movieName.setVisibility(View.INVISIBLE);

        }

        public void bindMovie(Result movie){
            this.movie = movie;

            movieName.setText(movie.getTitle());
            movieOverview.setText(movie.getOverview());
            movieScore.setText(String.valueOf(movie.getVoteAverage()));

            final String POSTER_PATH = Api.POSTER_PATH + movie.getPosterPath();
            Picasso.with(context).load(POSTER_PATH).into(moviePoster);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieDetails.class);
            intent.putExtra("MOVIE_NAME", movie.getTitle());
            intent.putExtra("MOVIE_ID", movie.getId());
            intent.putExtra("MOVIE_POSTER", movie.getBackdropPath());
            context.startActivity(intent);
        }
    }
}

