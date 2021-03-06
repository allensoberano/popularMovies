package com.example.android.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder> {

    private final List<Movie> mMovie;
    private final ItemClickListener mItemClickListener;
    private static final String POSTER_PATH = "http://image.tmdb.org/t/p/w185/";

    //Constructor
    public MovieRecyclerViewAdapter(List<Movie> movies, ItemClickListener listener) {

        //init member variables
        mMovie = movies;
        mItemClickListener = listener;

    }

    //When the RecyclerView inits a new view holder instance
    //Reference: RecyclerView Webcast

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(view);
    }

    //When RecyclerView wants to populate our view
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        if (mMovie != null) {
            Picasso.get()
                    .load(POSTER_PATH.concat(mMovie.get(position).getmPoster()))
                    .fit()
                    .placeholder(R.drawable.ic_local_movies_white_24dp)
                    .error(R.drawable.ic_local_movies_white_24dp)
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
            return mMovie.size();
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView listItemImageView;

        MovieViewHolder(View itemView) {
            super(itemView);

            listItemImageView = itemView.findViewById(R.id.iv_movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mItemClickListener.onItemClick(getAdapterPosition());

        }
    }
}
