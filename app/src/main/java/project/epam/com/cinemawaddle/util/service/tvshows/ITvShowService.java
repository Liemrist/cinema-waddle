package project.epam.com.cinemawaddle.util.service.tvshows;

import project.epam.com.cinemawaddle.util.service.ServiceResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITvShowService {
    /**
     * Get a list of shows that are currently on the air.
     * <p>
     * This query looks for any TV show that has an episode with an air date in the next 7 days.
     */
    @GET("tv/on_the_air")
    Call<ServiceResult<TvShow>> getOnTheAir(@Query("api_key") String apiKey,
                                            @Query("language") String language,
                                            @Query("page") int page);


    /**
     * Get a list of the top rated TV shows on TMDb.
     */
    @GET("tv/top_rated")
    Call<ServiceResult<TvShow>> getTopRated(@Query("api_key") String apiKey,
                                            @Query("language") String language,
                                            @Query("page") int page);

    /**
     * Get the list of your favorite TV shows.
     */
    @GET("account/{account_id}/favorite/tv")
    Call<ServiceResult<TvShow>> getFavourites(@Path("account_id") int accountId,
                                              @Query("api_key") String apiKey,
                                              @Query("language") String language,
                                              @Query("session_id") String sessionId,
                                              @Query("sort_by") String sortBy,
                                              @Query("page") int page);

    /**
     * Gets a list of all the objects you have added to your watchlist.
     */
    @GET("account/{account_id}/watchlist/tv")
    Call<ServiceResult<TvShow>> getWatchlist(@Path("account_id") int accountId,
                                             @Query("api_key") String apiKey,
                                             @Query("language") String language,
                                             @Query("session_id") String sessionId,
                                             @Query("sort_by") String sortBy,
                                             @Query("page") int page);


    /**
     * Get the primary TV show details by id.
     */
    @GET("tv/{tv_id}")
    Call<TvShowDetails> getDetails(@Path("tv_id") int groupId,
                                   @Query("api_key") String apiKey,
                                   @Query("language") String language,
                                   @Query("append_to_response") String appendToResponse);

}
