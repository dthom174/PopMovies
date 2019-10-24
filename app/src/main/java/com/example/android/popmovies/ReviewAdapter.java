package com.example.android.popmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    private List<String> mReviews;

    ReviewAdapter(ArrayList<String> reviews){
        mReviews = reviews;
    }

    public void setData(ArrayList<String> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_reviews, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mItem.setText( mReviews.get(position));
    }

    @Override
    public int getItemCount() {

        if(mReviews == null){
            return 0;
        }

        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.tv_detail_reviews);
        }
    }
}
