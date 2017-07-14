package project.epam.com.cinemawaddle.tabitem.view;

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
import project.epam.com.cinemawaddle.util.movies.Movie;

/**
 * RecyclerView adapter to display a list of {@link Movie}.
 */
class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    // fixme: Get this from getConfig request param, that you should store.
    private static final String SECURE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private List<Movie> movies;
    private Context context;
    private OnMovieClickListener listener;


    MoviesAdapter(Context context, OnMovieClickListener listener, List<Movie> movies) {
        this.context = context;
        this.listener = listener;
        this.movies = movies;
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.movie = movie;
        holder.titleView.setText(movie.getTitle());
        holder.genreView.setText(movie.getRelease_date());
        holder.ratingView.setText(String.valueOf(movie.getVote_average()));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClick(holder.movie);
            }
        });

        Glide.with(context)
                .load(SECURE_BASE_URL + holder.movie.getPoster_path())
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_placeholder_image)
                .fallback(R.drawable.ic_placeholder_image)
                .into(holder.posterView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title) TextView titleView;
        @BindView(R.id.text_genre) TextView genreView;
        @BindView(R.id.text_rating) TextView ratingView;
        @BindView(R.id.image_poster) ImageView posterView;

        public Movie movie;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}
