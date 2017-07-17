package project.epam.com.cinemawaddle.util.service;

import java.util.ArrayList;

public class ServiceResult<T> {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<T> results;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public int getTotalResults() {
        return total_results;
    }

    public ArrayList<T> getResults() {
        return results;
    }
}
