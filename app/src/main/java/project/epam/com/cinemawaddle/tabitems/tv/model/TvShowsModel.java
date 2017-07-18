package project.epam.com.cinemawaddle.tabitems.tv.model;


import android.content.Context;

import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.tabitems.base.BaseTabModelImpl;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.service.ServiceGenerator;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.service.tvshows.ITvShowService;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;
import retrofit2.Call;


public class TvShowsModel extends BaseTabModelImpl<TvShow, ServiceResult<TvShow>> implements ITvShowModel {

    private final ITvShowService client;

    public TvShowsModel(Context context) {
        super(context);
        client = ServiceGenerator.createService(ITvShowService.class);
    }

    // TODO: DELETE LANGUAGE FROM METHODS!!!1!
    @Override
    public void fetchTvShows(int type, String language, int page,
                             OnFinishedListener<ServiceResult<TvShow>> listener) {
        String language1 = preferences.getString(Constants.PREF_LOCALE, Constants.EN);

        Call<ServiceResult<TvShow>> call;

        switch (type) {
            case Constants.NOW_PLAYING:
                call = client.getOnTheAir(BuildConfig.TMDB_API_KEY, language1, page);
                break;
            case Constants.TOP_RATED:
                call = client.getTopRated(BuildConfig.TMDB_API_KEY, language1, page);
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
        String language1 = preferences.getString(Constants.PREF_LOCALE, Constants.EN);

        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<ServiceResult<TvShow>> call = client.getFavourites(accountId,
                BuildConfig.TMDB_API_KEY,
                language1,
                sessionId,
                sortBy,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }

    @Override
    public void fetchWatchlist(OnFinishedListener<ServiceResult<TvShow>> listener, String language,
                               String sortBy, int page) {
        String language1 = preferences.getString(Constants.PREF_LOCALE, Constants.EN);

        String sessionId = preferences.getString(Constants.PREF_SESSION_ID, null);
        int accountId = preferences.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);

        Call<ServiceResult<TvShow>> call = client.getWatchlist(accountId,
                BuildConfig.TMDB_API_KEY,
                language1,
                sessionId,
                sortBy,
                page);

        if (!call.isExecuted()) call.enqueue(getCallback(listener));
    }
}
