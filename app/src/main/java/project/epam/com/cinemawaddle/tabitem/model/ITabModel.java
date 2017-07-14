package project.epam.com.cinemawaddle.tabitem.model;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import project.epam.com.cinemawaddle.util.base.ModelBaseListener;
import project.epam.com.cinemawaddle.util.movies.Movie;

public interface ITabModel {
    void fetchMovies(OnFinishedListener listener);

    Intent createIntent(Context context, Movie movie);

    interface OnFinishedListener extends ModelBaseListener {
        void onResponse(List<Movie> items);
    }
}
