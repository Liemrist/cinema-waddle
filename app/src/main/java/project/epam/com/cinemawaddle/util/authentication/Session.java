package project.epam.com.cinemawaddle.util.authentication;

public final class Session {
    private final boolean success;
    private final String session_id;

    public Session(boolean success, String sessionId) {
        this.success = success;
        session_id = sessionId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSessionId() {
        return session_id;
    }
}
