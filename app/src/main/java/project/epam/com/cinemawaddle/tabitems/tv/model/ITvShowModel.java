package project.epam.com.cinemawaddle.tabitems.tv.model;

import project.epam.com.cinemawaddle.tabitems.BaseTabModel;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;
import project.epam.com.cinemawaddle.util.tvshows.TvServiceResult;


public interface ITvShowModel extends BaseTabModel<TvListResult> {

    void fetchTvShows(int type, String language, int page, OnFinishedListener<TvServiceResult> listener);

    void fetchFavorites(OnFinishedListener<TvServiceResult> listener);

    void fetchWatchlist(OnFinishedListener<TvServiceResult> listener);

}
