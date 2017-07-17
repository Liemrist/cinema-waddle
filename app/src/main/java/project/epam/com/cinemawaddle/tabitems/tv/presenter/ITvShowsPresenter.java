package project.epam.com.cinemawaddle.tabitems.tv.presenter;

import android.content.Context;

import java.util.List;

import project.epam.com.cinemawaddle.tabitems.BaseTabPresenter;
import project.epam.com.cinemawaddle.util.base.BasePresenter;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;

public interface ITvShowsPresenter extends BaseTabPresenter<TvListResult> {

    void loadFavorites();

    void loadWatchlist();

    void onMovieClicked(Context context, TvListResult movie);
}
