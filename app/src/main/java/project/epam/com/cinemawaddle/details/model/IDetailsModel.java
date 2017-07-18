package project.epam.com.cinemawaddle.details.model;

import android.content.Context;

import project.epam.com.cinemawaddle.util.base.ModelBaseListener;
import project.epam.com.cinemawaddle.util.service.BaseDetailsItem;
import project.epam.com.cinemawaddle.util.service.movies.Details;
import project.epam.com.cinemawaddle.util.service.movies.Movie;
import project.epam.com.cinemawaddle.util.service.movies.PostResponse;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShowDetails;

public interface IDetailsModel {

    void fetchMovieDetails(int id, OnFinishedListener listener);

    void fetchTvShowDetails(int id, OnFinishedListener listener);

    void setFavourite(String mediaType, int mediaId, boolean isFavorite,
                      OnFinishedListener listener);

    interface OnFinishedListener extends ModelBaseListener {
        void onFetchDetailsFinished(Details details);
        void onFetchDetailsFinished(TvShowDetails details);

        void onSetFavoriteFinished(PostResponse details);
    }
}

