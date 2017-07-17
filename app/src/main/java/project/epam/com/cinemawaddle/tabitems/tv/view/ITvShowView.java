package project.epam.com.cinemawaddle.tabitems.tv.view;

import java.util.List;

import project.epam.com.cinemawaddle.tabitems.BaseTabView;
import project.epam.com.cinemawaddle.util.base.BaseView;
import project.epam.com.cinemawaddle.util.tvshows.TvListResult;

public interface ITvShowView extends BaseTabView<TvListResult> {
    void showMoviesAfterUpdate(List<TvListResult> items, int totalPages);
}
