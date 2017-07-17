package project.epam.com.cinemawaddle.util.tvshows;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITvShowService {
    /**
     *Get a list of shows that are currently on the air.

     This query looks for any TV show that has an episode with an air date in the next 7 days.

     */
    @GET("tv/on_the_air")
    Call<TvServiceResult> getOnTheAir(@Query("api_key") String apiKey,
                                      @Query("language") String language,
                                      @Query("page") int page);


    /**
     * Get a list of the top rated TV shows on TMDb.
     */
    @GET("tv/top_rated")
    Call<TvServiceResult> getTopRated(@Query("api_key") String apiKey,
                             @Query("language") String language,
                             @Query("page") int page);

    /**Get the list of your favorite TV shows.
     */
    @GET("account/{account_id}/favorite/tv")
    Call<TvServiceResult> getFavourites(@Path("account_id") int accountId,
                               @Query("api_key") String apiKey,
                               @Query("language") String language,
                               @Query("session_id") String sessionId,
                               @Query("sort_by") String sortBy,
                               @Query("page") int page);

    /**
     * Gets a list of all the objects you have added to your watchlist.
     */
    @GET("account/{account_id}/watchlist/tv")
    Call<TvServiceResult> getWatchlist(@Path("account_id") int accountId,
                              @Query("api_key") String apiKey,
                              @Query("language") String language,
                              @Query("session_id") String sessionId,
                              @Query("sort_by") String sortBy,
                              @Query("page") int page);
}
