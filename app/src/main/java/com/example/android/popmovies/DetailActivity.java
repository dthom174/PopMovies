package com.example.android.popmovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popmovies.data.FavoriteMovies;
import com.example.android.popmovies.utilities.NetworkUtils;
import com.example.android.popmovies.utilities.ParseJson;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerClickerListener{
    private Movies mMovie;
    private TextView mTitle;
    private ImageView mThumbnail;
    private TextView mYear;
    private TextView mRuntime;
    private TextView mRating;
    private TextView mSynopsis;
    private CheckedTextView mCheckFavorite;
    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mTrailerRecycler;
    private ReviewAdapter mReviewAdapter;
    private RecyclerView mReviewRecycler;
    private LinearLayout mDetailView;
    private TextView mDetailError;
    private ProgressBar mDetailProgress;
    private TextView mErrorTrailer;
    private TextView mErrorReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("MovieDetail");

        mTitle = findViewById(R.id.tv_detail_title);
        mThumbnail = findViewById(R.id.tv_detail_thumbnail);
        mYear = findViewById(R.id.tv_detail_year);
        mRuntime = findViewById(R.id.tv_detail_runtime);
        mRating = findViewById(R.id.tv_detail_rating);
        mSynopsis = findViewById(R.id.tv_detail_synopsis);
        mCheckFavorite = findViewById(R.id.check_favorite);

        mDetailView = findViewById(R.id.detail_view);
        mDetailError = findViewById(R.id.tv_detail_error_display);
        mDetailProgress = findViewById(R.id.pb_detail_indicator);
        mErrorTrailer = findViewById(R.id.error_trailer);
        mErrorReview= findViewById(R.id.error_review);

        mTrailerAdapter = new TrailerAdapter(new ArrayList<String>(),this);
        mTrailerRecycler = findViewById(R.id.rv_detail_trailers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mTrailerRecycler.getContext());
        mTrailerRecycler.setLayoutManager(layoutManager);
        mTrailerRecycler.setHasFixedSize(true);
        mTrailerRecycler.setAdapter(mTrailerAdapter);

        mReviewAdapter = new ReviewAdapter(new ArrayList<String>());
        mReviewRecycler = findViewById(R.id.rv_detail_reviews);
        LinearLayoutManager layoutManager1= new LinearLayoutManager(mReviewRecycler.getContext());
        mReviewRecycler.setLayoutManager(layoutManager1);
        mReviewRecycler.setHasFixedSize(true);
        mReviewRecycler.setAdapter(mReviewAdapter);

        mMovie = new Movies();
        Intent intent = getIntent();
        mMovie.setmMovieId(intent.getIntExtra("MOVIE_ID",0));
        mMovie.setmImageUrl(intent.getStringExtra("POSTER"));

        showDetail();
        String url = "https://api.themoviedb.org/3/movie/";
        new DetailMovieTask().execute(url);

        mCheckFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mCheckFavorite.isChecked()){
                    mCheckFavorite.setChecked(true);
                    FavoriteMovies.movies.add(mMovie);
                }else{
                    mCheckFavorite.setChecked(false);
                    FavoriteMovies.movies.remove(mMovie);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_share_youtube){
            ArrayList<String> trailers= mMovie.getmTrailers();
            if(trailers.size() > 0){
                String mimeType = "text/plain";
                String title = "Share";
                ShareCompat.IntentBuilder
                        .from(this)
                        .setType(mimeType)
                        .setChooserTitle(title)
                        .setText("http://www.youtube.com/watch?v=" + trailers.get(1))
                        .startChooser();
            }else{
                Toast.makeText(this,getText(R.string.error_trailer),Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void TrailerGetItemClick(String trailer) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    public class DetailMovieTask extends AsyncTask<String, Void, Movies>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDetailProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movies doInBackground(String... urlString) {
            URL url = NetworkUtils.buildUrlForDetails(urlString[0], mMovie.getmMovieId() + "");
            URL urlReviews = NetworkUtils.buildUrlForReviews(urlString[0], mMovie.getmMovieId() + "");
            URL urlTrailers = NetworkUtils.buildUrlForTrailers(urlString[0], mMovie.getmMovieId() + "");

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                mMovie = ParseJson.getMovieDetailFromJson(DetailActivity.this, json, mMovie);

                String jsonReviews = NetworkUtils.getResponseFromHttpUrl(urlReviews);
                mMovie.setmReviews(ParseJson.getMovieReviewFromJson(DetailActivity.this, jsonReviews));

                String jsonTrailers =  NetworkUtils.getResponseFromHttpUrl(urlTrailers);
                mMovie.setmTrailers(ParseJson.getMovieTrailerFromJson(DetailActivity.this, jsonTrailers));

            } catch (Exception e) {
                return null;
            }
            return mMovie;
        }

        @Override
        protected void onPostExecute(Movies s) {
            mDetailProgress.setVisibility(View.INVISIBLE);
            if(s != null){
                showDetail();
                mCheckFavorite.setChecked(FavoriteMovies.movies.contains(s));
                mTitle.setText(s.getmTitle());
                Picasso.with(DetailActivity.this).load(s.getmImageUrl()).into(mThumbnail);
                mYear.setText(s.getmReleaseDate());
                mRuntime.setText(s.getmRuntime());
                mRating.setText(s.getmVote_average());
                mSynopsis.setText(s.getmOverview());

                if(!s.getmTrailers().isEmpty()){
                    showTrailers();
                    mTrailerAdapter.setData(s.getmTrailers());
                }else{
                    showTrailersError();
                }

                if(!s.getmReviews().isEmpty()){
                    showReviews();
                    mReviewAdapter.setData(s.getmReviews());
                }else{
                    showReviewsError();
                }
            }else{
                showDetailError();
            }
        }
    }

    public void showDetail(){
        mDetailError.setVisibility(View.INVISIBLE);
        mDetailView.setVisibility(View.VISIBLE);
    }

    public void showDetailError(){
        mDetailView.setVisibility(View.INVISIBLE);
        mDetailError.setVisibility(View.VISIBLE);
    }

    public void showTrailers(){
        mErrorTrailer.setVisibility(View.INVISIBLE);
        mTrailerRecycler.setVisibility(View.VISIBLE);
    }

    public void showTrailersError(){
        mTrailerRecycler.setVisibility(View.INVISIBLE);
        mErrorTrailer.setVisibility(View.VISIBLE);
    }

    public void showReviews(){
        mErrorReview.setVisibility(View.INVISIBLE);
        mReviewRecycler.setVisibility(View.VISIBLE);
    }

    public void showReviewsError(){
        mReviewRecycler.setVisibility(View.INVISIBLE);
        mErrorReview.setVisibility(View.VISIBLE);
    }
}
