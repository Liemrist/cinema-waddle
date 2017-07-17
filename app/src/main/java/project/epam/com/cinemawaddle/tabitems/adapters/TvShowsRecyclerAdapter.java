package project.epam.com.cinemawaddle.tabitems.adapters;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;


/**
 * RecyclerView adapter to display a list of {@link TvShow}.
 */
public class TvShowsRecyclerAdapter extends BaseAdapter<TvShow> {

    protected Context context;
    private OnMovieClickListener listener;

    public TvShowsRecyclerAdapter(Context context, OnMovieClickListener listener,
                                  List<TvShow> movies) {
        super(movies);
        this.context = context;
        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder holder, int position) {
        TvShow movie = objects.get(position);

        holder.object = movie;
        holder.titleView.setText(movie.getName());
        holder.genreView.setText(movie.getFirstAirDate());
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

    protected String getVote(TvShow movie) {
        double voteAverage = movie.getVoteAverage();
        return voteAverage == 0.0
                ? "--"
                : String.valueOf(voteAverage);
    }

    public interface OnMovieClickListener {
        void onMovieClick(TvShow movie);
    }
}
