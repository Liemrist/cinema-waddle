package project.epam.com.cinemawaddle.util.service;

import java.util.ArrayList;

import project.epam.com.cinemawaddle.util.service.movies.Details;

public abstract class BaseDetailsItem {
    int id;
    int vote_count;
    double popularity;
    double vote_average;
    String poster_path;
    String original_language;
    String overview;
    String backdrop_path;
    ArrayList<Genre> genres;
    String homepage;
    ArrayList<Object> production_companies;
    String status;

    public abstract String getName();

    public abstract String getReleaseDate();

    public String getOverview() {
        return overview;
    }

    private class Genre {
        int id;
        String name;
    }
}
