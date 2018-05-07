package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieRecyclerViewAdapater extends RecyclerView.Adapter<MovieRecyclerViewAdapater.MovieViewHolder> {

    private final Context mContext;
    private Movie[] mMovie = null;
    private ItemClickListener mItemClickListener;

    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185/";

    //Constructor
    public MovieRecyclerViewAdapater(Context context, Movie[] movies) {

        //init member variables
        mMovie = movies;
        mContext = context;

    }

    //When the RecyclerView Instantiats a new view holder instance
    //Reference: RecylerView Webcast
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    //When Recyclerview wants to populate our view
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        if (mMovie != null) {
            Picasso.with(mContext)
                    .load(POSTER_PATH.concat(mMovie[position].getmPoster()))
                    .fit()
                    .into(holder.listItemImageView);
        }
    }

    //returns the number of items in our data source
    @Override
    public int getItemCount() {

        //Feel like there is a better way than this
        if (mMovie == null) {
            return 20;
        } else {
            return mMovie.length;
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView listItemImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            listItemImageView = itemView.findViewById(R.id.iv_movie_image);
            listItemImageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (mItemClickListener != null) mItemClickListener.onItemClick(getAdapterPosition());

        }
    }
}
