package project.epam.com.cinemawaddle.util.service.authentication;

public final class Token {
    private final String request_token;

    public Token(String requestToken) {
        request_token = requestToken;
    }

    public String getRequestToken() {
        return request_token;
    }
}
