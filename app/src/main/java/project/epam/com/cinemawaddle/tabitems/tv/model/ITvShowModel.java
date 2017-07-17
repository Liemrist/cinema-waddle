package project.epam.com.cinemawaddle.tabitems.tv.model;

import project.epam.com.cinemawaddle.tabitems.BaseTabModel;
import project.epam.com.cinemawaddle.util.service.ServiceResult;
import project.epam.com.cinemawaddle.util.service.tvshows.TvShow;

public interface ITvShowModel extends BaseTabModel<TvShow, ServiceResult<TvShow>> {

    void fetchTvShows(int type, String language, int page,
                      OnFinishedListener<ServiceResult<TvShow>> listener);
}
