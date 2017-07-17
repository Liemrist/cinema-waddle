package project.epam.com.cinemawaddle.util.tvshows;

public class TvListResult {
    String poster_path;
    double popularity;
    int id;
    String backdrop_path;
    double vote_average;
    String overview;
    String first_air_date;
    String[] origin_country;
    int[] genre_ids;
    String original_language;
    int vote_count;
    String name;
    String original_name;

    public String getPoster_path() {
        return poster_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getId() {
        return id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String[] getOrigin_country() {
        return origin_country;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }
}
