package project.epam.com.cinemawaddle.util.movies;

import java.util.ArrayList;


public class Movies {
    private ArrayList<Movie> results;
    private Dates dates;
    private int page;
    private int total_pages;
    private int total_results;

    public ArrayList<Movie> getResults() {
        return results;
    }

    private class Dates {
        String maximum;
        String minimum;
    }
}
