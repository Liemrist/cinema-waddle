package project.epam.com.cinemawaddle.util.service.movies;

import project.epam.com.cinemawaddle.util.service.ServiceResult;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMoviesService {

    /**
     * This is a release type query that looks for all objects that
     * have a release type of 2 or 3 within the specified date range.
     *
     * @param apiKey   Developer's api key
     * @param language Value to display translated data for the fields that support it (default is en-US)
     * @param page     Specify which page to query.
     * @param region   Parameter which will narrow the search to only look for theatrical release dates within the specified country.
     * @return A list of objects in theatres.
     */
    @GET("movie/now_playing")
    Call<ServiceResult<Movie>> getNowPlaying(@Query("api_key") String apiKey,
                                             @Query("language") String language,
                                             @Query("page") int page,
                                             @Query("region") String region);


    /**
     * Gets the top rated objects on TMDb.
     */
    @GET("movie/top_rated")
    Call<ServiceResult<Movie>> getTopRated(@Query("api_key") String apiKey,
                                           @Query("language") String language,
                                           @Query("page") int page,
                                           @Query("region") String region);

    /**
     * This is a release type query that looks for all objects that
     * have a release type of 2 or 3 within the specified date range.
     *
     * @param apiKey   Developer's api key
     * @param language Value to display translated data for the fields that support it (default is en-US)
     * @param page     Specify which page to query.
     * @param region   Parameter which will narrow the search to only look for theatrical release dates within the specified country.
     * @return A list of upcoming objects in theatres.
     */
    @GET("movie/upcoming")
    Call<ServiceResult<Movie>> getUpcoming(@Query("api_key") String apiKey,
                                           @Query("language") String language,
                                           @Query("page") int page,
                                           @Query("region") String region);

    /**
     * Gets the list of your favorite objects.
     */
    @GET("account/{account_id}/favorite/movies")
    Call<ServiceResult<Movie>> getFavourites(@Path("account_id") int accountId,
                                             @Query("api_key") String apiKey,
                                             @Query("language") String language,
                                             @Query("session_id") String sessionId,
                                             @Query("sort_by") String sortBy,
                                             @Query("page") int page);

    /**
     * Gets a list of all the objects you have added to your watchlist.
     */
    @GET("account/{account_id}/watchlist/movies")
    Call<ServiceResult<Movie>> getWatchlist(@Path("account_id") int accountId,
                                            @Query("api_key") String apiKey,
                                            @Query("language") String language,
                                            @Query("session_id") String sessionId,
                                            @Query("sort_by") String sortBy,
                                            @Query("page") int page);

    /**
     * Gets the primary information about a object.
     */
    @GET("movie/{movie_id}")
    Call<Details> getDetails(@Path("movie_id") int groupId,
                             @Query("api_key") String apiKey,
                             @Query("language") String language,
                             @Query("append_to_response") String appendToResponse);

    /**
     * Allows you to mark a object or TV show as a favorite item.
     */
    @FormUrlEncoded
    @POST("account/{account_id}/favorite")
    Call<PostResponse> setFavorite(@Path("account_id") int accountId,
                                   @Query("api_key") String apiKey,
                                   @Query("session_id") String sessionId,
                                   @Field("media_type") String mediaType,
                                   @Field("media_id") int mediaId,
                                   @Field("favorite") boolean favorite);
}
