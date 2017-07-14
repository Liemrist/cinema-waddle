package project.epam.com.cinemawaddle.util.movies;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMoviesService {

    /**
     * This is a release type query that looks for all movies that
     * have a release type of 2 or 3 within the specified date range.
     *
     * @param apiKey   Developer's api key
     * @param language Value to display translated data for the fields that support it (default is en-US)
     * @param page     Specify which page to query.
     * @param region   Parameter which will narrow the search to only look for theatrical release dates within the specified country.
     * @return A list of upcoming movies in theatres.
     */
    @GET("movie/upcoming")
    Call<Movies> getUpcoming(@Query("api_key") String apiKey,
                             @Query("language") String language,
                             @Query("page") int page,
                             @Query("region") String region);

    /**
     * This is a release type query that looks for all movies that
     * have a release type of 2 or 3 within the specified date range.
     *
     * @param apiKey   Developer's api key
     * @param language Value to display translated data for the fields that support it (default is en-US)
     * @param page     Specify which page to query.
     * @param region   Parameter which will narrow the search to only look for theatrical release dates within the specified country.
     * @return A list of movies in theatres.
     */
    @GET("movie/now_playing")
    Call<Movies> getNowPlaying(@Query("api_key") String apiKey,
                               @Query("language") String language,
                               @Query("page") int page,
                               @Query("region") String region);

    /**
     * Gets the list of your favorite movies.
     */
    @GET("account/{account_id}/favorite/movies")
    Call<Movies> getFavourites(@Path("account_id") int accountId,
                               @Query("api_key") String apiKey,
                               @Query("session_id") String sessionId,
                               @Query("language") String language,
                               @Query("sort_by") String sortBy,
                               @Query("page") int page);

    /**
     * Gets the primary information about a movie.
     */
    @GET("movie/{movie_id}")
    Call<Details> getDetails(@Path("movie_id") int groupId,
                             @Query("api_key") String apiKey,
                             @Query("language") String language,
                             @Query("append_to_response") String appendToResponse);

    /**
     * Allows you to mark a movie or TV show as a favorite item.
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
