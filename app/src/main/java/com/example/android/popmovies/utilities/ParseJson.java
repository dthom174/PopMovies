package com.example.android.popmovies.utilities;

import android.content.Context;

import com.example.android.popmovies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParseJson {

    public static ArrayList<Movies> getMoviesPosterFromJson(Context context, String json) throws JSONException {
        final String mBaseUrl = "https://image.tmdb.org/t/p/w185/";
        ArrayList<Movies> listOfMovies = new ArrayList<>();

        JSONObject root = new JSONObject(json);
        JSONArray results = root.optJSONArray("results");

        for(int i = 0; i < results.length(); i++){

            JSONObject object = results.optJSONObject(i);
            int movieId = object.getInt("id");
            String imageUrl = mBaseUrl + object.getString("poster_path");
            listOfMovies.add(new Movies(imageUrl,movieId));
        }

        return listOfMovies;
    }

    public static Movies getMovieDetailFromJson(Context context, String json, Movies currentMovie) throws JSONException{
        JSONObject root = new JSONObject(json);

        currentMovie.setmTitle(root.getString("original_title"));
        currentMovie.setmReleaseDate(root.getString("release_date").substring(0,4));
        currentMovie.setmRuntime(root.getInt("runtime") + "min");
        currentMovie.setmOverview( root.getString("overview"));
        currentMovie.setmVote_average(root.getDouble("vote_average")+"/10");

        return currentMovie;
    }

    public static ArrayList<String> getMovieTrailerFromJson(Context context, String json) throws JSONException{
        ArrayList<String> trailers = new ArrayList<>();
        JSONObject root = new JSONObject(json);
        JSONArray results = root.optJSONArray("results");

        for(int i = 0; i < results.length(); i++){
            JSONObject object = results.optJSONObject(i);

            if(object.getString("type").equals("Trailer")){

                trailers.add(object.getString("key"));
            }
        }

        return trailers;
    }

    public static ArrayList<String> getMovieReviewFromJson(Context context, String json) throws JSONException{
        ArrayList<String> reviews = new ArrayList<>();
        JSONObject root = new JSONObject(json);
        JSONArray results = root.optJSONArray("results");

        for(int i = 0; i < results.length(); i++){
            JSONObject object = results.optJSONObject(i);
            String author = object.getString("author");
            reviews.add("Author: "+ author + "\n\n" + object.getString("content"));
        }

        return reviews;
    }
}
