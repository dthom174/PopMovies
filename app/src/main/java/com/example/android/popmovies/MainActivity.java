package com.example.android.popmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popmovies.data.FavoriteMovies;
import com.example.android.popmovies.utilities.NetworkUtils;
import com.example.android.popmovies.utilities.ParseJson;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.onClickListenerMovies{

    private final String urlTopRate = "https://api.themoviedb.org/3/movie/top_rated";
    private final String urlPopular = "https://api.themoviedb.org/3/movie/popular";
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorDisplay;
    private TextView mNoFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRecyclerView.getContext(),2);
        mMovieAdapter = new MovieAdapter(this,this,new ArrayList<Movies>());

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = findViewById(R.id.pb_indicator);
        mErrorDisplay = findViewById(R.id.tv_error_display);
        mNoFavorite = findViewById(R.id.tv_no_favorite);

        loadMoviePoster(urlTopRate);
    }

    @Override
    public void interfaceTask(Movies movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("MOVIE_ID", movie.getmMovieId());
        intent.putExtra("POSTER", movie.getmImageUrl());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_top_rated){
            loadMoviePoster(urlTopRate);
            return true;
        }
        if(id == R.id.menu_popular){
            loadMoviePoster(urlPopular);
            return true;
        }
        if(id == R.id.menu_favorite){
            loadFavoriteMovie();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadMoviePoster(String url){
        showPosters();
        new Postertask().execute(url);
    }

    public void loadFavoriteMovie(){
        if(!FavoriteMovies.movies.isEmpty()){
            showPosters();
            mMovieAdapter.setData(FavoriteMovies.movies);

        }else{
            showNoFavorite();
        }
    }

    public void showPosters(){
        mErrorDisplay.setVisibility(View.INVISIBLE);
        mNoFavorite.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorDisplay.setVisibility(View.VISIBLE);
    }

    public void showNoFavorite(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorDisplay.setVisibility(View.INVISIBLE);
        mNoFavorite.setVisibility(View.VISIBLE);
    }

    public class Postertask extends AsyncTask<String, Void, ArrayList<Movies>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movies> doInBackground(String... urlString) {

            URL url = NetworkUtils.buildUrlForMain(urlString[0]);
            ArrayList<Movies> movies;

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                movies = ParseJson.getMoviesPosterFromJson(MainActivity.this, json);
            } catch (Exception e) {
                return null;
            }


            return movies;
        }

        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(movies != null && movies.size() != 0){
                showPosters();
                mMovieAdapter.setData(movies);

            }else{
                showErrorMessage();
            }

        }
    }
}
