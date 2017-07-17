package project.epam.com.cinemawaddle.tabitems;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.BaseServiceArrayItem;


public class CinemaRecyclerAdapter<T extends BaseServiceArrayItem>
        extends RecyclerView.Adapter<CinemaRecyclerAdapter.ViewHolder> {

    private List<T> objects;
    private final Context context;
    private final OnMovieClickListener<T> listener;


    public CinemaRecyclerAdapter(Context context, OnMovieClickListener<T> listener, List<T> objects) {
        this.objects = objects;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public CinemaRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CinemaRecyclerAdapter.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(CinemaRecyclerAdapter.ViewHolder holder, int position) {
        T movie = objects.get(position);

        holder.object = movie;
        holder.titleView.setText(movie.getName());
        holder.genreView.setText(movie.getReleaseDate());
        holder.ratingView.setText(getVote(movie));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClick(movie);
            }
        });

        Glide.with(context)
                .load(Constants.SECURE_BASE_IMAGE_URL + movie.getPosterPath())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_no_poster)
                .fallback(R.drawable.ic_no_poster)
                .into(holder.posterView);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private String getVote(T movie) {
        double voteAverage = movie.getVoteAverage();
        return voteAverage == 0.0
                ? "--"
                : String.valueOf(voteAverage);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title) TextView titleView;
        @BindView(R.id.text_date) TextView genreView;
        @BindView(R.id.text_rating) TextView ratingView;
        @BindView(R.id.image_poster) ImageView posterView;

        T object;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnMovieClickListener<T> {
        void onMovieClick(T movie);
    }
}
