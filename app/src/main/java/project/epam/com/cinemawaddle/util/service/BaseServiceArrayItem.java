package project.epam.com.cinemawaddle.util.service;

public abstract class BaseServiceArrayItem {
    private int id;
    private int vote_count;
    private int[] genre_ids;
    private double popularity;
    private double vote_average;
    private String poster_path;
    private String overview;
    private String original_language;
    private String backdrop_path;

    public abstract String getName();

    public abstract String getReleaseDate();

    public int getId() {
        return id;
    }

    public int getVoteCount() {
        return vote_count;
    }

    public int[] getGenreIds() {
        return genre_ids;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }
}
