package com.example.android.popmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private List<String> mTrailers;
    private TrailerClickerListener mClicker;

    TrailerAdapter(ArrayList<String> trailers, TrailerClickerListener clicker){
        mTrailers = trailers;
        mClicker = clicker;
    }

    public void setData(ArrayList<String> trailers){
        mTrailers =trailers;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_trailers, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mItem.setText("Trailer " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public interface TrailerClickerListener{

        void TrailerGetItemClick(String trailer);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.tv_trailer_number);
            mItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClicker.TrailerGetItemClick(mTrailers.get(getAdapterPosition()));
        }
    }

}
