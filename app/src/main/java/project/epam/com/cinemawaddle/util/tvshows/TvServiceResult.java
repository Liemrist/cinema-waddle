package project.epam.com.cinemawaddle.util.tvshows;

import java.util.ArrayList;

/**
 * Created by Alexander on 7/16/2017.
 */

public class TvServiceResult {
    int page;
    private ArrayList<TvListResult> results;
    int total_results;
    int total_pages;

    public int getPage() {
        return page;
    }

    public ArrayList<TvListResult> getResults() {
        return results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotalPages() {
        return total_pages;
    }
}
