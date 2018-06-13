package com.example.android.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;


public class TrailerRVAdapter extends RecyclerView.Adapter<TrailerRVAdapter.TrailerViewHolder> {

    private final Trailer[] mTrailers;
    private static final String THUMBNAIL_PATH = "https://img.youtube.com/vi/";
    @SuppressWarnings("SpellCheckingInspection")
    private static final String THUMBNAIL_IMAGE = "/hqdefault.jpg";
    private final ItemClickListener mItemClickListener;

    //Constructor
    public TrailerRVAdapter(Trailer[] trailers, ItemClickListener listener) {

        //init member variables
        mTrailers = trailers;
        mItemClickListener = listener;
    }




    @NonNull
    @Override
    public TrailerRVAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_list_item, parent, false);

        return new TrailerRVAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerRVAdapter.TrailerViewHolder holder, int position) {

        if (mTrailers != null) {
            Picasso.get()
                    .load(THUMBNAIL_PATH.concat(mTrailers[position].getKey()).concat(THUMBNAIL_IMAGE))
                    .fit()
                    .into(holder.listItemImageView);
        }

    }

    @Override
    public int getItemCount() {
        return  mTrailers == null ? 10 : mTrailers.length;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final ImageView listItemImageView;

        TrailerViewHolder(View itemView) {
            super(itemView);

            listItemImageView = itemView.findViewById(R.id.iv_movie_trailer_image);
            //listItemImageView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(getAdapterPosition());

        }
    }

}
