package project.epam.com.cinemawaddle.details.model;

import android.content.Context;

import project.epam.com.cinemawaddle.util.base.ModelBaseListener;
import project.epam.com.cinemawaddle.util.movies.Details;
import project.epam.com.cinemawaddle.util.movies.PostResponse;

public interface IDetailsModel {
    void fetchDetails(int id, OnFinishedListener listener);

    void setFavourite(Context context, String mediaType, int mediaId, boolean isFavorite,
                      OnFinishedListener listener);

    interface OnFinishedListener extends ModelBaseListener {
        void onFetchDetailsFinished(Details details);

        void onSetFavoriteFinished(PostResponse details);
    }
}

