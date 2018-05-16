package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;

public class ReviewRVAdapter extends RecyclerView.Adapter<ReviewRVAdapter.ReviewViewHolder>
{

    private final Context mContext;
    private final Review[] mReviews;

    //Constructor
    public ReviewRVAdapter(Context context, Review[] reviews) {

        //init member variables
        mReviews = reviews;
        mContext = context;

    }

    @Override
    public ReviewRVAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);

        return new ReviewViewHolder (view);
    }

    @Override
    public void onBindViewHolder(ReviewRVAdapter.ReviewViewHolder holder, int position) {
        if (mReviews != null){
            holder.bind(position);
        }

    }

    @Override
    public int getItemCount() {
        return  mReviews == null ? 10 : mReviews.length;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{
        final TextView listItemTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            listItemTextView = itemView.findViewById(R.id.tv_review);
        }

        //convenience method
        void bind(int listIndex){
            listItemTextView.setText(String.valueOf(listIndex));
        }

    }
}
