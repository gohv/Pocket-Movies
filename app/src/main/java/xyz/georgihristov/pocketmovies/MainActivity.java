package xyz.georgihristov.pocketmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView movieList;
    private MovieListAdapter adapter;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private List<Result> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        movieList = (ListView) findViewById(R.id.movieList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);

        loadList(Api.GET_POPULAR_MOVIES + Api.API_KEY);

        setupDrawer();
        addDrawerItems();
        onItemClick();

    }

    private class Executor extends AsyncTask<String, List<Result>, Void> {
        final Downloader downloader = new Downloader();


        @Override
        protected Void doInBackground(String... params) {

            String apiToGet = params[0];

            try {
                Movie movieResults = downloader.movieResults(apiToGet);
                publishProgress(movieResults.getResults());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(List<Result>... values) {
            adapter.movies.addAll(values[0]);
            adapter.notifyDataSetChanged();
        }
    }

    private void setupDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    private void addDrawerItems() {
        final String[] goods = {"Now Playing", "High Rated", "Upcoming", "Popular"};

        NavigationAdapter listAdapter = new NavigationAdapter(this, goods);
        drawerList.setAdapter(listAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (drawerLayout != null) {
                    drawerLayout.closeDrawer(drawerList);
                }
                switch (position) {
                    case 0:// now_playing
                        loadList(Api.GET_NOW_PLAYING);
                        break;
                    case 1://high_rated
                        loadList(Api.GET_HIGHEST_RATED_MOVIES + Api.API_KEY);

                        break;
                    case 2://upcoming_movies
                        loadList(Api.GET_UPCOMING_MOVIES);

                        break;
                    case 3://popular_movies
                        loadList(Api.GET_POPULAR_MOVIES + Api.API_KEY);
                        break;
                    default:
                }
            }
        });
    }

    private void loadList(String json) {
        adapter = new MovieListAdapter(MainActivity.this, new ArrayList<Result>());
        new Executor().execute(json);
        movieList.setAdapter(adapter);
        movies = adapter.movies;

    }

    private void onItemClick() {
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                intent.putExtra("MOVIE_NAME", movies.get(position).getTitle());
                intent.putExtra("MOVIE_ID", movies.get(position).getId());
                intent.putExtra("MOVIE_POSTER", movies.get(position).getBackdropPath());
                startActivity(intent);
            }
        });
    }
}
