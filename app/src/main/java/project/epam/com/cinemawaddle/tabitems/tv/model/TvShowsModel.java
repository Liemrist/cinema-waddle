package project.epam.com.cinemawaddle.tabitems.tv.model;


import android.content.Context;

import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.tabitems.BaseTabModelImpl;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceGenerator;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.service.tvshows.ITvShowService;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;
import retrofit2.Call;


public class TvShowsModel extends BaseTabModelImpl<TvShow, ServiceResult<TvShow>> implements ITvShowModel {

    private ITvShowService client;

    public TvShowsModel(Context context) {
        super(context);
        client = ServiceGenerator.createService(ITvShowService.class);
    }

    @Override
    public void fetchTvShows(int type, String language, int page,
                             OnFinishedListener<ServiceResult<TvShow>> listener) {
        Call<ServiceResult<TvShow>> call;

        switch (type) {
            case Constants.NOW_PLAYING:
                call = client.getOnTheAir(BuildConfig.TMDB_API_KEY, language, page);
                break;
            case Constants.TOP_RATED:
                call = client.getTopRated(BuildConfig.TMDB_API_KEY, language, page);
                break;
            default:
                call = null;
        }

        if (call == null) listener.onFetchingEnd(null);
        else if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchFavorites(OnFinishedListener<ServiceResult<TvShow>> listener, String language,
                               String sortBy, int page) {
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<ServiceResult<TvShow>> call = client.getFavourites(accountId,
                BuildConfig.TMDB_API_KEY,
                language,
                sessionId,
                sortBy,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchWatchlist(OnFinishedListener<ServiceResult<TvShow>> listener, String language,
                               String sortBy, int page) {
        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<ServiceResult<TvShow>> call = client.getWatchlist(accountId,
                BuildConfig.TMDB_API_KEY,
                language,
                sessionId,
                sortBy,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }
}
