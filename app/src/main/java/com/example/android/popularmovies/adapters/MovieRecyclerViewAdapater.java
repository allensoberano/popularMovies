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

    private int mNumberItems;
    private final Context mContext;
    private Movie[] mMovie = null;

    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/W185";
    //private static final String POSTER_SIZE = "w185";

    //Constructor
    public MovieRecyclerViewAdapater(Context context, Movie[] movies ){
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
        //MovieViewHolder viewHolder = new MovieViewHolder(view);
        return new MovieViewHolder(view);
    }

    //When Recyclerview wants to populate our view
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(POSTER_PATH.concat(mMovie[position].getmPoster()))
                .fit()
                .into(holder.listItemImageView);

        //Picasso.load("http://i.imgur.com/DvpvklR.png").into(holder.listItemImageView);

    }

    //returns the number of items in our data source
    @Override
    public int getItemCount() {
        return mMovie.length;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView listItemImageView;

        public MovieViewHolder (View itemView) {
            super(itemView);

            listItemImageView = (ImageView) itemView.findViewById(R.id.iv_movie_image);
        }


        @Override
        public void onClick(View v) {

        }
    }
}
