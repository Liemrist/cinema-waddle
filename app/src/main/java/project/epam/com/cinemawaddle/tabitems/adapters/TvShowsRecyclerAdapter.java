package project.epam.com.cinemawaddle.tabitems.adapters;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;


/**
 * RecyclerView adapter to display a list of {@link TvListResult}.
 */
public class TvShowsRecyclerAdapter extends BaseAdapter<TvListResult> {

    protected Context context;
    private OnMovieClickListener listener;

    public TvShowsRecyclerAdapter(Context context, OnMovieClickListener listener,
                                  List<TvListResult> movies) {
        super(movies);
        this.context = context;
        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(BaseAdapter.ViewHolder holder, int position) {
        TvListResult movie = objects.get(position);

        holder.object = movie;
        holder.titleView.setText(movie.getName());
        holder.genreView.setText(movie.getFirst_air_date());
        holder.ratingView.setText(getVote(movie));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClick(movie);
            }
        });

        Glide.with(context)
                .load(Constants.SECURE_BASE_IMAGE_URL + movie.getPoster_path())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_no_poster)
                .fallback(R.drawable.ic_no_poster)
                .into(holder.posterView);
    }

    protected String getVote(TvListResult movie) {
        double voteAverage = movie.getVoteAverage();
        return voteAverage == 0.0
                ? "--"
                : String.valueOf(voteAverage);
    }

    public interface OnMovieClickListener {
        void onMovieClick(TvListResult movie);
    }
}
