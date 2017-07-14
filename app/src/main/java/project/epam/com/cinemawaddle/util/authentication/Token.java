package project.epam.com.cinemawaddle.util.authentication;

public final class Token {
    private final boolean success;
    private final String expires_at;
    private final String request_token;

    public Token(boolean success, String expiresAt, String requestToken) {
        this.success = success;
        expires_at = expiresAt;
        request_token = requestToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getExpiresAt() {
        return expires_at;
    }

    public String getRequestToken() {
        return request_token;
    }
}
