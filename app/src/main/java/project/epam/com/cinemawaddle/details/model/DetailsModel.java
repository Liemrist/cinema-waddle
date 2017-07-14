package project.epam.com.cinemawaddle.details.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import project.epam.com.cinemawaddle.BuildConfig;
import project.epam.com.cinemawaddle.util.Constants;
import project.epam.com.cinemawaddle.util.ServiceGenerator;
import project.epam.com.cinemawaddle.util.movies.IMoviesService;
import project.epam.com.cinemawaddle.util.movies.Details;
import project.epam.com.cinemawaddle.util.movies.PostResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsModel implements IDetailsModel {

    @Override
    public void fetchDetails(int id, OnFinishedListener listener) {
        IMoviesService client = ServiceGenerator.createService(IMoviesService.class);
        Call<Details> call = client.getDetails(id, BuildConfig.TMDB_API_KEY, null, null);

        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(@NonNull Call<Details> call,
                                   @NonNull Response<Details> response) {
                if (response.body() == null) return;
                listener.onFetchDetailsFinished(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Details> call, @NonNull Throwable t) {
                listener.onFailed(Constants.ERROR_MESSAGE_NETWORK);
            }
        });
    }

    @Override
    public void setFavourite(Context context, String mediaType, int mediaId, boolean isFavorite, OnFinishedListener listener) {

        SharedPreferences sharedPref =
                context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        int accountId = sharedPref.getInt(Constants.PREF_ACCOUNT_ID, Constants.DEFAULT_ACCOUNT_ID);
        String sessionId = sharedPref.getString(Constants.PREF_SESSION_ID, null);

        IMoviesService client = ServiceGenerator.createService(IMoviesService.class);
        Call<PostResponse> call = client.setFavorite(
                accountId,
                BuildConfig.TMDB_API_KEY,
                sessionId,
                mediaType,
                mediaId,
                isFavorite);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.body() == null) return;
                listener.onSetFavoriteFinished(response.body());
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
    }
}
