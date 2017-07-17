package project.epam.com.cinemawaddle.util.service;

import java.util.ArrayList;

public final class ServiceResult<T> {
    private final int page;
    private final int total_results;
    private final int total_pages;
    private final ArrayList<T> results;

    public ServiceResult(int page, int total_results, int total_pages, ArrayList<T> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

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
