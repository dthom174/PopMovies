package com.example.android.popmovies;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movies> mListOfMovies;
    private Context mContext;
    private onClickListenerMovies mPosterClicker;

    MovieAdapter(Activity context, onClickListenerMovies clicker, List<Movies> list){
        mListOfMovies = list;
        mContext = context;
        mPosterClicker = clicker;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_poster_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String imageUrl = mListOfMovies.get(position).getmImageUrl();

        if(imageUrl == null || imageUrl.isEmpty()){
            return;
        }
        Picasso.with(mContext).load(imageUrl).into(viewHolder.mItem);
    }

    @Override
    public int getItemCount() {

        return mListOfMovies.size();
    }

    public interface onClickListenerMovies{

        void interfaceTask(Movies movie);

    }

    public void setData(List<Movies> listOfMovies){

        mListOfMovies = listOfMovies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.image_poster);
            mItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mPosterClicker.interfaceTask(mListOfMovies.get(getAdapterPosition()));
        }
    }

}
