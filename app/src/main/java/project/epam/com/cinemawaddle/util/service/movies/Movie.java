package project.epam.com.cinemawaddle.util.service.movies;

import project.epam.com.cinemawaddle.util.service.BaseServiceArrayItem;

public class Movie extends BaseServiceArrayItem {

    private String title;
    private String original_title;
    private String release_date;
    private boolean adult;
    private boolean video;

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getReleaseDate() {
        return release_date;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public boolean isAdult() {
        return adult;
    }

    public boolean isVideo() {
        return video;
    }

}
