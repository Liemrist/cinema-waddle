package project.epam.com.cinemawaddle.util.service.tvshows;

import project.epam.com.cinemawaddle.util.service.BaseServiceArrayItem;

public class TvShow extends BaseServiceArrayItem {

    private String name;
    private String original_name;
    private String first_air_date;
    private String[] origin_country;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getReleaseDate() {
        return first_air_date;
    }

    public String getOriginalName() {
        return original_name;
    }

    public String[] getOriginCountry() {
        return origin_country;
    }
}
