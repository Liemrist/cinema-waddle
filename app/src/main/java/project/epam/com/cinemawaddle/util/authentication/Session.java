package project.epam.com.cinemawaddle.util.authentication;

public final class Session {
    private final String session_id;

    public Session(String sessionId) {
        session_id = sessionId;
    }

    public String getSessionId() {
        return session_id;
    }
}
