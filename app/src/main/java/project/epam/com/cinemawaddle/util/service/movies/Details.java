package project.epam.com.cinemawaddle.util.service.movies;

import java.util.ArrayList;

import project.epam.com.cinemawaddle.util.service.BaseDetailsItem;

public class Details extends BaseDetailsItem {
    String title;
    String original_title;
    String release_date; //1 validations

    boolean adult;
    Object belongs_to_collection;
    int budget;
    String imdb_id; // 3 validations
    ArrayList<Object> spoken_languages;
    ArrayList<Object> production_countries;
    int revenue;
    int runtime;
    String tagline;
    boolean video;

    @Override
    public String getName() {
        return title;
    }

    @Override
    public String getReleaseDate() {
        return release_date;
    }
}
