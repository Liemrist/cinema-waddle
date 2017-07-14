package project.epam.com.cinemawaddle.util.authentication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAuthenticationService {

    /**
     * Creates a temporary request token that can be used to validate a TMDb user login.
     */
    @GET("authentication/token/new")
    Call<Token> createToken(@Query("api_key") String apiKey);

    /**
     * Creates a fully valid session ID once a user has validated the request token.
     */
    @GET("authentication/session/new")
    Call<Session> createSession(@Query("api_key") String apiKey,
                                @Query("request_token") String requestToken);

    /** Get your account details. */
    @GET("account")
    Call<Account> getDetails(@Query("api_key") String apiKey,
                             @Query("session_id") String sessionId);
}
