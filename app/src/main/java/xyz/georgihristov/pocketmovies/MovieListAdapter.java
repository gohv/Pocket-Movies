package xyz.georgihristov.pocketmovies;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


class MovieListAdapter extends BaseAdapter {

    private final Context context;
    public final List<Result> movies;

    public MovieListAdapter(Context context, List<Result> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.movies_list_items, null);
            viewHolder = new ViewHolder();

            viewHolder.movieName = (TextView) convertView.findViewById(R.id.movieName);
            viewHolder.movieScore = (TextView) convertView.findViewById(R.id.movieScore);
            viewHolder.movieOverview = (TextView) convertView.findViewById(R.id.movieOverview);
            viewHolder.moviePoster = (ImageView) convertView.findViewById(R.id.moviePoster);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Result movie = movies.get(position);
        viewHolder.movieName.setText(movie.getTitle());
        viewHolder.movieOverview.setText(movie.getOverview());
        viewHolder.movieScore.setText(String.valueOf(movie.getVoteAverage()));


        final String POSTER_PATH = Api.POSTER_PATH + movie.getPosterPath();
        Picasso.with(context).load(POSTER_PATH).into(viewHolder.moviePoster);
        viewHolder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenImage.class);
                intent.putExtra("IMAGE_", POSTER_PATH);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView moviePoster; //public by default
        TextView movieName;
        TextView movieOverview;
        TextView movieScore;
    }
}
