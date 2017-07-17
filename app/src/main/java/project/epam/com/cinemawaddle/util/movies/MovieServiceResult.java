package project.epam.com.cinemawaddle.util.movies;

import java.util.ArrayList;


public class MovieServiceResult {
    private ArrayList<Movie> results;
    private int page;
    private int total_pages;
    private int total_results;

    public int getTotalPages() {
        return total_pages;
    }

    public int getTotalResults() {
        return total_results;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }
}
