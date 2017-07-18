package project.epam.com.cinemawaddle.util.service.tvshows;

import java.util.ArrayList;

import project.epam.com.cinemawaddle.util.service.BaseDetailsItem;

/**
 * Created by Alexander on 7/18/2017.
 */

public class TvShowDetails extends BaseDetailsItem {
    ArrayList<Object> created_by;
    int[] episode_run_time;
    String first_air_date;
    boolean in_production;
    String[] languages;
    String last_air_date;
    String name;
    ArrayList<Object> networks;
    int number_of_episodes;
    int number_of_seasons;
    String[] origin_country;
    String original_name;
    ArrayList<Object> seasons;
    String type;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getReleaseDate() {
        return first_air_date;
    }
}
