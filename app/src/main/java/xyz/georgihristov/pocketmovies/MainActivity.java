package xyz.georgihristov.pocketmovies;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import shortbread.Shortbread;
import shortbread.Shortcut;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieList;
    private MovieRecyclerAdapter adapter;
    private DrawerLayout drawerLayout;
    private ListView drawerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        movieList = (RecyclerView) findViewById(R.id.movieList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerList = (ListView) findViewById(R.id.drawerList);

        loadList(Api.GET_POPULAR_MOVIES + Api.API_KEY);
        movieList.setLayoutManager(new GridLayoutManager(this, 2));
        Shortbread.create(this);
        setupDrawer();
        addDrawerItems();
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
                        nowPlayingMovies();
                        break;
                    case 1://high_rated
                        highRatedMovies();
                        break;
                    case 2://upcoming_movies
                        upcomingMovies();
                        break;
                    case 3://popular_movies
                        popularMovies();
                        break;
                    default:
                }
            }
        });
    }

    private void loadList(String json) {

        adapter = new MovieRecyclerAdapter(MainActivity.this, new ArrayList<Result>());
        new Executor().execute(json);
        movieList.setAdapter(adapter);


    }

    @Shortcut(id = "high_rated", icon = R.drawable.ic_launcher, shortLabel = "High Rated")
    public void highRatedMovies(){
        loadList(Api.GET_HIGHEST_RATED_MOVIES + Api.API_KEY);
    }
    @Shortcut(id = "now_playing", icon = R.drawable.ic_launcher, shortLabel = "Now Playing")
    public void nowPlayingMovies(){
        loadList(Api.GET_NOW_PLAYING);
    }
    @Shortcut(id = "upcoming", icon = R.drawable.ic_launcher, shortLabel = "Upcoming")
    public void upcomingMovies(){
        loadList(Api.GET_UPCOMING_MOVIES);
    }
    @Shortcut(id = "popular", icon = R.drawable.ic_launcher, shortLabel = "Popular")
    public void popularMovies(){
        loadList(Api.GET_POPULAR_MOVIES + Api.API_KEY);
    }
}
